#!/bin/bash

# Default values
VERSION="latest"
WORKDIR=$(pwd)

# Function to print usage
print_usage() {
    echo "Usage: $0 -m MODULE [-j JAR_NAME] [-i IMAGE_NAME] [-v VERSION] [-s]"
    echo
    echo "Options:"
    echo "  -m MODULE      Required. The module name (e.g., app, nebula)"
    echo "  -j JAR_NAME    Optional. The jar file name. Defaults to MODULE"
    echo "  -i IMAGE_NAME  Optional. The Docker image name. Defaults to violabs/constellation-MODULE"
    echo "  -v VERSION     Optional. The version tag. Defaults to 'latest'"
    echo "  -w WORKDIR     Optional. The working directory. Defaults to current directory"
    exit 1
}

# Parse command line arguments
while getopts ":m:j:i:v:w" opt; do
    case $opt in
        m) MODULE="$OPTARG" ;;
        j) JAR_NAME="$OPTARG" ;;
        i) IMAGE_NAME="$OPTARG" ;;
        v) VERSION="$OPTARG" ;;
        w) WORKDIR="$OPTARG" ;;
        \?) echo "Invalid option -$OPTARG" >&2; print_usage ;;
        :) echo "Option -$OPTARG requires an argument." >&2; print_usage ;;
    esac
done

# Check for required module argument
if [ -z "$MODULE" ]; then
    echo "Error: Module name (-m) is required"
    print_usage
fi

# Set defaults if not provided
if [ -z "$JAR_NAME" ]; then
    JAR_NAME="$MODULE"
fi

if [ -z "$IMAGE_NAME" ]; then
    IMAGE_NAME="$MODULE"
fi

FULL_IMAGE_NAME="violabs/$IMAGE_NAME:$VERSION"

# Change to the working directory
echo "Setting working directory to: $WORKDIR"
cd "$WORKDIR" || {
    echo "Failed to change to working directory"
    exit 1
}

# Build the image
echo "Building image: $FULL_IMAGE_NAME"
docker build \
  --build-arg SERVICE_NAME="$MODULE" \
  --build-arg SERVICE_JAR_NAME="$JAR_NAME" \
  --build-arg VERSION="$VERSION" \
  -t "$FULL_IMAGE_NAME" \
  -f Dockerfile || {
    echo "Failed to build image"
    exit 1
}