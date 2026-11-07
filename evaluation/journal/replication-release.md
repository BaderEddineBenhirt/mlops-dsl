# Replication and manuscript-release gate

These are open checkboxes, not completed evidence.

Use `tools/build_replication_package.py` and the commands in [execution.md](execution.md)
to collect the actual metamodel, JUnit XML, raw observations and environment.
The collector creates a draft archive only after input preflight; it does not
publish a release or verify unauthenticated reviewer access.

- [ ] Stable tagged SHA, time of execution, source/input hashes and license.
- [ ] External Ecore meta-model and .genmodel provided with redistribution
      rights; OCL registration independently reproducible.
- [ ] Xtext grammar/generator/validators and Eclipse target/Java 21/Xtext
      2.40.0 versions pinned; clean-room build demonstrated.
- [ ] JUnit XML and console diagnostics exported and committed/archived.
- [ ] Terraform/provider lock files, kubectl/Kustomize versions and Python
      dependencies identified and reproducible.
- [ ] All frozen cases, requirements, seed mutations and paired source files.
- [ ] Independent baseline review of K8s, Terraform and lifecycle policies.
- [ ] All raw fault and evolution observations, including misses/blocked cases.
- [ ] Source-size metric definitions and explicit duplication-review notes.
- [ ] Cloud inference/lifecycle evidence and teardown logs where claimed.
- [ ] Claim-to-evidence traceability table and manuscript numbers reconciled.
- [ ] Redaction of secrets and company data without discarding failures.
- [ ] Independent reviewer can obtain a stable released archive/DOI.
      A private GitHub repository alone is not enough.

Automation unit tests cannot stand in for scientific experiment executions.

