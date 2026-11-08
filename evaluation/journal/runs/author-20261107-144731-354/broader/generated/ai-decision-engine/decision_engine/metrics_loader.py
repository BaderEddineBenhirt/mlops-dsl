from pathlib import Path
import json

def load_json(path):
    path = Path(path)
    if not path.exists():
        raise FileNotFoundError(f'JSON file not found: {path}')
    with path.open('r', encoding='utf-8') as file:
        return json.load(file)

def save_json(data, path):
    path = Path(path)
    path.parent.mkdir(parents=True, exist_ok=True)
    with path.open('w', encoding='utf-8') as file:
        json.dump(data, file, indent=2)

def load_model_metrics(path):
    metrics = load_json(path)
    if 'candidates' not in metrics:
        raise ValueError("Invalid model metrics: missing 'candidates' list.")
    return metrics

def load_drift_report(path):
    drift = load_json(path)
    if 'drift_score' not in drift:
        raise ValueError("Invalid drift report: missing 'drift_score'.")
    return drift
