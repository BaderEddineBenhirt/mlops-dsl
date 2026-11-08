from pathlib import Path
import argparse
import json
import numpy as np

from sklearn.datasets import load_breast_cancer
from sklearn.model_selection import train_test_split
from decision_engine.drift_detector import average_wasserstein_score

ROOT = Path(__file__).resolve().parents[1]
DEFAULT_OUTPUT = ROOT / 'outputs' / 'drift_report.json'

def simulate_drift(output_path, noise_level=2.5):
    output_path = Path(output_path)
    output_path.parent.mkdir(parents=True, exist_ok=True)
    dataset = load_breast_cancer()
    x_train, x_test, _, _ = train_test_split(
        dataset.data, dataset.target, test_size=0.25, random_state=42, stratify=dataset.target
    )
    rng = np.random.default_rng(42)
    x_drifted = x_test.copy()
    x_drifted[:, :5] = x_drifted[:, :5] + rng.normal(
        loc=noise_level, scale=noise_level, size=x_drifted[:, :5].shape
    )
    drift_score = average_wasserstein_score(x_train, x_drifted)
    report = {
        'dataset_version': 'v1_drifted',
        'method': 'average_wasserstein_distance',
        'drift_score': drift_score,
        'drift_detected': drift_score >= 0.30,
        'threshold': 0.30
    }
    with output_path.open('w', encoding='utf-8') as file:
        json.dump(report, file, indent=2)
    print(json.dumps(report, indent=2))

if __name__ == '__main__':
    parser = argparse.ArgumentParser()
    parser.add_argument('--output', default=str(DEFAULT_OUTPUT))
    parser.add_argument('--noise-level', type=float, default=2.5)
    args = parser.parse_args()
    simulate_drift(args.output, args.noise_level)
