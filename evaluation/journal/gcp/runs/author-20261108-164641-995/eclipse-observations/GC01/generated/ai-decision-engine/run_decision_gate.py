import argparse
from pathlib import Path

from decision_engine.policy_loader import load_decision_policy, load_retraining_policy, load_monitoring_policy
from decision_engine.metrics_loader import load_model_metrics, load_drift_report, save_json
from decision_engine.decision_gate import run_decision_gate
from decision_engine.retraining_advisor import run_retraining_advisor

ROOT = Path(__file__).resolve().parent
PROJECT_ROOT = ROOT.parent

def ensure_neutral_drift(path):
    path = Path(path)
    if path.exists():
        return
    save_json({
        'dataset_version': 'v1',
        'method': 'neutral_default',
        'drift_score': 0.0,
        'drift_detected': False,
        'threshold': 0.30
    }, path)

def main():
    parser = argparse.ArgumentParser()
    parser.add_argument('--policy', default=str(PROJECT_ROOT / 'mlops' / 'decision-gate.yaml'))
    parser.add_argument('--retraining', default=str(PROJECT_ROOT / 'mlops' / 'retraining-policy.yaml'))
    parser.add_argument('--monitoring', default=str(PROJECT_ROOT / 'mlops' / 'monitoring-policy.yaml'))
    parser.add_argument('--metrics', default=str(ROOT / 'outputs' / 'model_metrics.json'))
    parser.add_argument('--drift', default=str(ROOT / 'outputs' / 'drift_report.json'))
    parser.add_argument('--decision-output', default=str(ROOT / 'outputs' / 'promotion_decision.json'))
    parser.add_argument('--retraining-output', default=str(ROOT / 'outputs' / 'retraining_recommendation.json'))
    args = parser.parse_args()

    ensure_neutral_drift(args.drift)

    decision_policy = load_decision_policy(args.policy)
    retraining_policy = load_retraining_policy(args.retraining)
    load_monitoring_policy(args.monitoring)
    model_metrics = load_model_metrics(args.metrics)
    drift_report = load_drift_report(args.drift)

    decision_result = run_decision_gate(decision_policy, model_metrics, drift_report)
    retraining_result = run_retraining_advisor(retraining_policy, drift_report, decision_result)

    save_json(decision_result, args.decision_output)
    save_json(retraining_result, args.retraining_output)
    print('Decision Gate completed.')
    print(f'Decision output: {Path(args.decision_output).resolve()}')
    print(f'Retraining output: {Path(args.retraining_output).resolve()}')

if __name__ == '__main__':
    main()
