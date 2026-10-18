# Generated MLOps Artifacts

Project: `validation_c00`

## Generated layers

- `infra/terraform/`: Terraform platform infrastructure.
- `pipelines/`: CI/CD/CT pipeline model.
- `mlops/`: tracking, versioning, registry, decision gate, retraining and governance policies.
- `runtime/k8s/`: Kubernetes runtime descriptors for the external inference application.
- `ai-decision-engine/`: executable AI-assisted decision layer consuming generated MLOps policies.

## AI Decision Gate execution

```bash
cd ai-decision-engine
python -m venv .venv
source .venv/bin/activate  # Windows: .venv\Scripts\activate
pip install -r requirements.txt
python experiments/train_candidates.py
python experiments/simulate_drift.py
python run_decision_gate.py
```

The decision engine reads `../mlops/decision-gate.yaml`, `../mlops/retraining-policy.yaml`, and `../mlops/monitoring-policy.yaml` automatically. No manual copy is required.

## Terraform validation

```bash
cd infra/terraform
terraform init
terraform fmt -recursive
terraform validate
terraform plan -var-file=terraform.tfvars.example -out=tfplan
terraform apply tfplan
```

## Scope note

The generated artifacts provide the MLOps automation, governance, infrastructure, and AI-assisted decision layer. The generated AI decision engine trains baseline candidate models for evaluation and consumes generated decision/retraining/monitoring policies automatically. The production ML application, Dockerfile and inference API may remain in an external repository.
