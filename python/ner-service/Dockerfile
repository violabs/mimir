# Use a lightweight Python base image
FROM python:3.12-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the requirements file into the container
COPY requirements.txt .

# Install Python dependencies
RUN pip install --no-cache-dir -r requirements.txt

# Download the spacy model
# Make sure this matches the model loaded in app.py
RUN python -m spacy download en_core_web_lg

# Copy the application code into the container
COPY app/ .

# Expose the port the Flask app runs on
EXPOSE 5001

# Command to run the Flask application
CMD ["python", "app.py"]