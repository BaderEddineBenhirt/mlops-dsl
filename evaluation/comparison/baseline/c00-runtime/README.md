# C00 directly authored runtime baseline

This is an assistant-assisted direct Kubernetes/Kustomize configuration, prepared from the selected C00 runtime requirements. It is not a timed human authoring experiment. The generated snapshot was inspected to identify the implemented output boundary; the files here were separately authored, not copied and relabeled as manual work.

## Frozen pilot scope

Namespace `vision-serving`, two serving replicas, API port 8080 propagated to container/probes/Service/Ingress, public host `vision.azure.company.com`, Ingress class `nginx`, and TLS Secret reference `validation-c00-serving-tls`. The image and probe paths match the emitted C00 runtime configuration. These four runtime objects do not implement the complete C00 training, infrastructure, registry, monitoring or retraining specification. The image/application contents, certificate provisioning and live deployment are not evaluated.

Use Kustomize replacements to propagate the single Service port into five dependent fields, and the routing host into the TLS host. This allows a competent existing-tool baseline to reuse values. A port evolution task can change one baseline source field, just as it can change one DSL source field; a tie is a valid result.

The checked-in Deployment/probe/Ingress ports and TLS host are template placeholders. Render them through Kustomize before evaluating; do not compare the unrendered inputs with deployment outputs.

## Execute the paired pilot

From the repository root, check whether `kubectl` is available:

```powershell
kubectl version --client
```

If missing, the official Windows installation command is `winget install -e --id Kubernetes.kubectl`; reopen PowerShell after installation. Record the actual installed version, including its bundled Kustomize version. The runner logs the client version. This pilot uses local `kubectl kustomize`, not apply or a cluster connection.

```powershell
& .\.venv-evaluation\Scripts\python.exe tools\run_runtime_comparison.py `
  --out evaluation\comparison\runs\c00-runtime-pair-01
```

The generated side defaults to the immutable `generated/c00-after-tls` snapshot. The runner builds the direct baseline with the real kubectl executable, splits the four rendered objects, then applies the same unchanged ten selected requirements to both sides. It archives command output, client version, file hashes and results in a new run directory. Missing tools/build failures are blocked observations, not successful fault detection. A zero exit code establishes that selected requirements pass on both sides, not that either method is superior or fully deployed.

## Interpretation and next measurements

Do not compare full DSL line counts against this partial runtime baseline as equivalent workload sizes. Source inventories and rendered inventories are separate; no reduction percentage or authoring-time claim is computed. Before expanding the study, independently review the frozen requirements and each representation's coverage. The report deliberately retains `full_functional_equivalence: not_reviewed`.

For a controlled port task, start from fresh copies, change DSL `ServingSpec.port` and baseline `Service.spec.ports[0].port` from 8080 to 9090, regenerate/render, and use a copied contract with all port expectations changed to 9090. Archive both source diffs and output checks. Record actual source edit locations separately from rendered changes. This task has not been executed by adding these files.

Next extend coverage to independently authored Terraform and lifecycle policies, then equivalent fault pairs. Until those trials and review are complete, this is a scoped runtime comparison pilot only.

## Primary tool references

- Kubernetes Authors, Kustomize management and replacements, accessed 2026-10-18: https://kubernetes.io/docs/tasks/manage-kubernetes-objects/kustomization/
- Kubernetes Authors, Windows kubectl installation, accessed 2026-10-18: https://kubernetes.io/docs/tasks/tools/install-kubectl-windows/
