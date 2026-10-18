# Real cloud deployment and end-to-end verification — NOT RUN

Generated configuration, syntax checks, Kustomize build, Terraform validate or
a passing JUnit suite **do not establish a live deployment**.

If the manuscript claims deployment, use a dedicated sandbox with a cost limit,
least-privilege service identity, documented cloud, region, immutable container
digest, Terraform/provider lock, Kubernetes version and sanitized topology.
Never put credentials, secrets or customer data into research artifacts.

Record actual: terraform init/validate, security-reviewed plan, approved apply,
cluster API resource admission, deployment rollout and ready probes, ingress
controller/DNS/TLS Secret prerequisites, certificate verification, and **real
inference response** (not just a health endpoint). Check monitoring, model
registry, decision gate and retraining events separately *only where
implemented*. If YAML describes intentions but no executor consumes it,
mark the operational claim unsupported.

Preserve stdout, stderr, exit codes, timestamps, redacted resource identities,
configuration snapshot, failure/recovery outcomes and cleanup/cost evidence.
A real fault or blocked resource is a legitimate negative observation.
No plan/apply/deployment occurs as part of this PR.
