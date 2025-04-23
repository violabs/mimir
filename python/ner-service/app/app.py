from flask import Flask, request, jsonify
import spacy

# Load the spaCy model. 'en_core_web_sm' is the small English model.
# Load it once when the app starts for efficiency.
# Use 'en_core_web_md' if you downloaded the medium model.
try:
    # nlp = spacy.load("en_core_web_sm")
    # nlp = spacy.load("en_core_web_md") # Uncomment if using the medium model
    nlp = spacy.load("en_core_web_lg") # Uncomment if using the large model
except OSError:
    print("SpaCy model 'en_core_web_sm' not found. Please download it by running:")
    print("python -m spacy download en_core_web_sm")
    exit() # Exit if the model isn't found

app = Flask(__name__)

@app.route('/ner', methods=['POST'])
def perform_ner():
    if not request.json or 'text' not in request.json:
        return jsonify({"error": "Please provide 'text' in the request body"}), 400

    text = request.json['text']
    doc = nlp(text)

    # Extract entities and format the output
    entities = []
    for ent in doc.ents:
        entities.append({
            "text": ent.text,
            "label": ent.label_,
            "start_char": ent.start_char,
            "end_char": ent.end_char
        })

    return jsonify({"entities": entities})

# This is the entry point to run the Flask app
if __name__ == '__main__':
    # Run the app on all available interfaces (0.0.0.0) and port 5001
    # debug=True is useful for development, turn off for production
    app.run(host='0.0.0.0', port=5001, debug=False)