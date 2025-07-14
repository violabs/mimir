package io.violabs.mimir.kafka.schemaregistry.avro.controller

import io.violabs.mimir.kafka.schemaregistry.avro.service.UserEventService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/users")
class UserController(
    private val userEventService: UserEventService
) {

    @PostMapping("/events")
    fun createUserEvent(
        @RequestParam userId: String,
        @RequestParam action: String,
        @RequestParam(required = false) email: String?
    ): ResponseEntity<Map<String, String>> {
        
        userEventService.sendUserEvent(userId, action, email)
        
        return ResponseEntity.ok(mapOf(
            "message" to "User event sent successfully",
            "userId" to userId,
            "action" to action
        ))
    }
    
    @PostMapping("/{userId}/create")
    fun createUser(@PathVariable userId: String, @RequestParam email: String): ResponseEntity<Map<String, String>> {
        userEventService.sendUserEvent(userId, "USER_CREATED", email)
        return ResponseEntity.ok(mapOf(
            "message" to "User creation event sent",
            "userId" to userId
        ))
    }
    
    @PutMapping("/{userId}/update")
    fun updateUser(@PathVariable userId: String, @RequestParam(required = false) email: String?): ResponseEntity<Map<String, String>> {
        userEventService.sendUserEvent(userId, "USER_UPDATED", email)
        return ResponseEntity.ok(mapOf(
            "message" to "User update event sent",
            "userId" to userId
        ))
    }
    
    @DeleteMapping("/{userId}")
    fun deleteUser(@PathVariable userId: String): ResponseEntity<Map<String, String>> {
        userEventService.sendUserEvent(userId, "USER_DELETED")
        return ResponseEntity.ok(mapOf(
            "message" to "User deletion event sent",
            "userId" to userId
        ))
    }
}