from pathlib import Path
import json

ROOT = Path(__file__).resolve().parents[1]
METRICS = ROOT / 'outputs' / 'model_metrics.json'

if __name__ == '__main__':
    if not METRICS.exists():
        raise FileNotFoundError('model_metrics.json not found. Run train_candidates.py first.')
    with METRICS.open('r', encoding='utf-8') as file:
        metrics = json.load(file)
    ranked = sorted(metrics['candidates'], key=lambda item: item.get('f1', 0.0), reverse=True)
    print(json.dumps({'ranked_candidates': ranked}, indent=2))
