from flask import Flask, jsonify

# Create a Flask app
app = Flask(__name__)

# Define an API route
@app.route('/api/data', methods=['GET'])
def get_data():
    data = {
        'message': 'Hello from the API!',
        'device': 'Device on the same router',
        # Add any other data you want to provide
    }
    return jsonify(data)

if __name__ == '__main__':
    # Run the Flask app
    app.run(host='0.0.0.0', port=5000)
