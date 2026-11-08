# AI Decision Engine

This module is generated together with the MDA/MDE artifacts for `validation_c00`.
It consumes the generated MLOps policy descriptors from `../mlops/` and produces executable decision outputs.

## Local execution

```bash
cd ai-decision-engine
python -m venv .venv
source .venv/bin/activate  # Windows: .venv\\Scripts\\activate
pip install -r requirements.txt
python experiments/train_candidates.py
python experiments/simulate_drift.py
python run_decision_gate.py
```

## Generated outputs

- `outputs/model_metrics.json`
- `outputs/drift_report.json`
- `outputs/promotion_decision.json`
- `outputs/retraining_recommendation.json`
