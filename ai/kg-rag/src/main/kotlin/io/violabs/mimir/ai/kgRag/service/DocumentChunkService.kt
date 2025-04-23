package io.violabs.mimir.ai.kgRag.service

import io.violabs.mimir.ai.kgRag.domain.entity.DocumentChunk
import io.violabs.mimir.ai.kgRag.domain.entity.ProcessDoc
import io.violabs.mimir.ai.kgRag.repository.ProcessDocRepository
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.stereotype.Service

// Regex to find headers like == Header ==
// - (?m) enables MULTILINE mode (^ and $ match start/end of lines, not just start/end of string)
// - ^\s* matches the start of a line followed by optional whitespace
// - == matches the opening marker
// - \s*(.*?)\s* captures the header text (non-greedily), trimming surrounding whitespace within the markers
// - == matches the closing marker
// - \s*$ matches optional whitespace followed by the end of the line
private val SECTION_TEMPLATE = Regex("""(?m)^\s*==\s*(.*?)\s*==\s*$""")

private data class TextSection(val header: String?, val content: String)

@Service
class DocumentChunkService(
    private val processDocRepository: ProcessDocRepository
) {
    suspend fun chunkAndSave(doc: ProcessDoc, content: String): ProcessDoc {
        val chunks = extractSectionsByHeader(content)

        val (docName) = doc

        doc.chunks = chunks
            .mapIndexed { i, chunk ->
                DocumentChunk(
                    docName = docName,
                    index = i,
                    content = chunk.content,
                    sectionName = chunk.header
                )
            }

        return processDocRepository.save(doc).awaitSingleOrNull() ?: doc
    }

    private fun extractSectionsByHeader(text: String): List<TextSection> {
        val sections = mutableListOf<TextSection>()

        val matches = SECTION_TEMPLATE.findAll(text).toList() // Get all header matches

        var currentPosition = 0 // Keep track of where we are in the original text

        // 1. Handle text before the first header (preamble)
        if (matches.isNotEmpty()) {
            val firstMatchStart = matches.first().range.first
            if (firstMatchStart > 0) {
                val preambleContent = text.substring(0, firstMatchStart).trim()
                if (preambleContent.isNotEmpty()) {
                    sections.add(TextSection(header = null, content = preambleContent))
                }
            }
            // Update currentPosition to the end of the first matched header line
            currentPosition = matches.first().range.last + 1
        } else {
            // No headers found, the entire text is considered content without a header
            val trimmedText = text.trim()
            if (trimmedText.isNotEmpty()) {
                sections.add(TextSection(header = null, content = trimmedText))
            }
            return sections // Nothing more to do
        }

        // 2. Process each header and its content
        matches.forEachIndexed { index, matchResult ->
            val headerText = matchResult.groupValues[1].trim() // Captured group 1 is the header text

            // Determine the end of the content for *this* header
            // It's the start of the *next* header, or the end of the text if this is the last header
            val contentEnd = if (index + 1 < matches.size) {
                matches[index + 1].range.first // Start index of the next header match
            } else {
                text.length // End of the entire input string
            }

            // Extract the content, making sure start index isn't past the end index
            val content = if (currentPosition < contentEnd) {
                text.substring(currentPosition, contentEnd).trim()
            } else {
                "" // No content between this header and the next (or end of string)
            }

            // Add the section (even if content is empty, the header itself is significant)
            sections.add(TextSection(header = headerText, content = content))

            // Update currentPosition for the next iteration
            // It should point to the character *after* the current header's content ends
            // which is already handled by setting it to the start of the next content block
            currentPosition = if (index + 1 < matches.size) {
                matches[index + 1].range.last + 1 // Position after the next header line
            } else {
                text.length // Reached the end
            }
        }

        return sections
    }
}