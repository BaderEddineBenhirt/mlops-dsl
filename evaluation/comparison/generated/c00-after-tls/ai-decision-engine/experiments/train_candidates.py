from pathlib import Path
import argparse
import json
import time
import joblib

from sklearn.datasets import load_breast_cancer
from sklearn.ensemble import RandomForestClassifier, GradientBoostingClassifier
from sklearn.linear_model import LogisticRegression
from sklearn.metrics import accuracy_score, f1_score, precision_score, recall_score
from sklearn.model_selection import train_test_split
from sklearn.pipeline import Pipeline
from sklearn.preprocessing import StandardScaler

ROOT = Path(__file__).resolve().parents[1]
DEFAULT_OUTPUT = ROOT / 'outputs' / 'model_metrics.json'
MODELS_DIR = ROOT / 'outputs' / 'models'

def measure_latency_ms(model, x_test, repeat=100):
    sample = x_test[:1]
    start = time.perf_counter()
    for _ in range(repeat):
        model.predict(sample)
    end = time.perf_counter()
    return ((end - start) / repeat) * 1000

def train_and_evaluate(output_path):
    output_path = Path(output_path)
    output_path.parent.mkdir(parents=True, exist_ok=True)
    MODELS_DIR.mkdir(parents=True, exist_ok=True)
    dataset = load_breast_cancer()
    x_train, x_test, y_train, y_test = train_test_split(
        dataset.data, dataset.target, test_size=0.25, random_state=42, stratify=dataset.target
    )
    candidates = {
        'logistic_regression_v1': Pipeline([('scaler', StandardScaler()), ('model', LogisticRegression(max_iter=1000))]),
        'random_forest_v1': RandomForestClassifier(n_estimators=100, random_state=42),
        'gradient_boosting_v1': GradientBoostingClassifier(random_state=42)
    }
    results = []
    for model_id, model in candidates.items():
        model.fit(x_train, y_train)
        predictions = model.predict(x_test)
        model_path = MODELS_DIR / f'{model_id}.joblib'
        joblib.dump(model, model_path)
        result = {
            'model_id': model_id,
            'accuracy': round(float(accuracy_score(y_test, predictions)), 4),
            'precision': round(float(precision_score(y_test, predictions)), 4),
            'recall': round(float(recall_score(y_test, predictions)), 4),
            'f1': round(float(f1_score(y_test, predictions)), 4),
            'latency_ms': round(float(measure_latency_ms(model, x_test)), 4),
            'model_size_mb': round(float(model_path.stat().st_size / (1024 * 1024)), 4),
            'cost': 0.10,
            'traceability_score': 1.0,
            'artifact_path': str(model_path)
        }
        results.append(result)
    output = {
        'project': 'validation-c00',
        'dataset': 'sklearn_breast_cancer',
        'dataset_version': 'v1',
        'candidates': results
    }
    with output_path.open('w', encoding='utf-8') as file:
        json.dump(output, file, indent=2)
    print(json.dumps(output, indent=2))

if __name__ == '__main__':
    parser = argparse.ArgumentParser()
    parser.add_argument('--output', default=str(DEFAULT_OUTPUT))
    args = parser.parse_args()
    train_and_evaluate(args.output)
