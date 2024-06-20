from flask import Flask, request, jsonify
import cv2
from mtcnn import MTCNN
import torch
import numpy as np
import torch.nn as nn
from PIL import Image

app = Flask(__name__)


from transformers import AutoImageProcessor, AutoModelForImageClassification

processor = AutoImageProcessor.from_pretrained("AkshatSurolia/ConvNeXt-FaceMask-Finetuned")
model = AutoModelForImageClassification.from_pretrained("AkshatSurolia/ConvNeXt-FaceMask-Finetuned")

if isinstance(model.classifier, nn.Sequential):
    in_features = model.classifier[-1].in_features
else:
    in_features = model.classifier.in_features

model.classifier = nn.Linear(in_features, 4, bias=True)


checkpoint = torch.load('C:\workspace\.venv\emotional camera\CONVNextmodel_save.pth', map_location=torch.device('cpu'))

model.load_state_dict(checkpoint['model'])
model.eval()  


# 이미지 전처리 함수 정의
def preprocess(image):
    #image = cv2.cvtColor(image, cv2.COLOR_BGR2RGB)
    # numpy 배열을 tensor로 변환 및 정규화
    image = image.astype(np.float32) / 255.0
    image = (image - np.array([0.5630258, 0.44020364, 0.49802032])) / np.array([0.24872069, 0.20780964, 0.18916753])
    image = np.transpose(image, (2, 0, 1))  # HWC -> CHW
    image = torch.tensor(image, dtype=torch.float32)
    image = image.unsqueeze(0)  # 배치 차원 추가
    return image

@app.route('/')
def index():
    return "Hello, World!"

@app.route('/process', methods=['POST'])
def process_data():
    if 'image' not in request.files:
        return jsonify({'error': 'No image file provided'}), 400
    
    image_file = request.files['image']
    image = Image.open(image_file).convert('RGB')
    image = image.rotate(270, expand=True)
    frame = np.array(image)
    detector = MTCNN()
    results = detector.detect_faces(frame)
    emotions =[]
    for f in results:
        x, y, width, height = f['box']
        crop = frame[y:y+height, x:x+width]
        crop = cv2.resize(crop, (224, 224))
        image = preprocess(crop)
        with torch.no_grad():
            outputs = model(image)

        if hasattr(outputs, 'logits'):
            logits = outputs.logits
        else:
            logits = outputs
        _, predicted = torch.max(logits, 1)
        emotionlist=['angry', 'happy', 'panic', 'sad']
        emotion=emotionlist[predicted.item()]
        emotions.append(emotion)
        print(emotion)
    return jsonify(result=emotions)

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000,debug=True)






