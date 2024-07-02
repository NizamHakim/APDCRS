from flask import Flask, request, jsonify
import os
import tensorflow as tf
import numpy as np

def load_image(filepath):
    image = tf.keras.preprocessing.image.load_img(filepath, target_size=(224, 224))
    img_arr = tf.keras.preprocessing.image.img_to_array(image)
    img_arr = np.array([img_arr])
    img_arr = img_arr / 255.0
    return img_arr 
    

app = Flask(__name__)

UPLOAD_FOLDER = 'images'
if not os.path.exists(UPLOAD_FOLDER):
    os.makedirs(UPLOAD_FOLDER)
app.config['UPLOAD_FOLDER'] = UPLOAD_FOLDER

@app.route('/classifier', methods=['POST'])
def upload_file():
    plant_dictionary = {
        0 : "Jagung : Karat Daun",
        1 : "Jagung : Bercak Daun",
        2 : "Jagung : Tanaman Sehat",
        3 : "Jagung : Hawar Daun Utara",
        4 : "Tomat : Bercak Bateri",
        5 : "Tomat : Bercak Kering",
        6 : "Tomat : Tanaman Sehat",
        7 : "Tomat : Busuk Daun",
        8 : "Tomat : Bercak Daun",
        9 : "Tomat : Bintik Daun Septoria",
        10 : "Tomat : Spider Mite",
        11 : "Tomat : Bercak Target",
        12 : "Tomat : Keriting Bule"
    }
    
    file = request.files['image']

    filepath = os.path.join(app.config['UPLOAD_FOLDER'], file.filename)
    file.save(filepath)
    
    # Get image
    image = load_image(filepath)

    # Load model
    model = tf.keras.models.load_model('model.h5')
    prediction = model.predict(image)

    TRESHOLD = 0.99
    result = np.argmax(prediction)
    confidence = np.max(prediction)
    print(f"Result: {result}, Confidence: {confidence}")
    if confidence < TRESHOLD: result = -1
    print(f"Final Result: {result}")
    
    return jsonify({'result': int(result)})

if __name__ == '__main__':
    app.run(host="192.168.1.113", port=5000)
