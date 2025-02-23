#!/bin/bash
set -e  # Exit on error
set -o pipefail  # Exit if a command in a pipeline fails

CHANGED_FILES="$1"

if echo "$CHANGED_FILES" | grep -q -E "^(build.gradle.kts|settings.gradle.kts)$"; then
    echo "Detected change in build scripts - running all modules."
    MODULES=$(./gradlew -q printModules | jq -R -s 'split("\n")[:-1]')

    {
      echo "matrix<<EOF"
      echo "${MODULES}"
      echo "EOF"
    } >> "$GITHUB_OUTPUT"
    exit 0
fi

if echo "$CHANGED_FILES" | grep -q -v -E "^(.github/|build.gradle.kts|settings.gradle.kts|[^/]+/src/)"; then
    echo "Detected only non-module root changes - skipping build."

    {
      echo "matrix=[]"
    } >> "$GITHUB_OUTPUT"

    echo "**Skipped. No change.**" >> "$GITHUB_STEP_SUMMARY"
    exit 0
fi

CHANGED_MODULES=$(echo "$CHANGED_FILES" | awk -F'/' '{print $1}' | sort -u | jq -R -s 'split("\n")[:-1]')

# Ensure correct submodule names based on Gradle settings
VALID_MODULES=$(./gradlew -q printModules | jq -R -s 'split("\n")[:-1]')
FILTERED_MODULES=$(echo "$CHANGED_MODULES" | jq -c --argjson valid "$VALID_MODULES" 'map(select(. | IN($valid[])))')

if [[ -z "$FILTERED_MODULES" || "$FILTERED_MODULES" == "[]" ]]; then
    echo "No relevant module changes detected."

    {
      echo "matrix=[]"
    } >> $GITHUB_OUTPUT

    echo "**Skipped. No change.**" >> "$GITHUB_STEP_SUMMARY"
    exit 0
fi

{
  echo "matrix<<EOF"
  echo "${FILTERED_MODULES}"
  echo "EOF"
} >> "$GITHUB_OUTPUT"
