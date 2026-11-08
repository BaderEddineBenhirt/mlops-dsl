# Direct C00 Azure configuration baseline

Assistant-assisted source authored for the C00 requirements. These files were
written separately from the generated snapshot; they are not observations of
human manual authoring or a timed participant trial. The existing Kustomize
baseline in `evaluation/comparison/baseline/c00-runtime` supplies runtime objects
and centralizes the API port and TLS host. The comparison runner renders it.

Scope: Azure virtual network/subnets, AKS/system and compute pools, storage
account/private containers, vault, log workspace, four Kubernetes objects, nine
MLOps policy documents. Terraform uses provider **3.117.1**, within the generator's
3.x constraint; changing to provider 4.x would change the comparison.

Policies describe the same configured values. They do not establish that an
external engine executes CI, training, registration, drift monitoring or
retraining. Azure resource-group organization and names differ from generated
modules. No service was deployed. The model's Datadog intent versus generated
Azure log workspace, serving environment/resources/command omissions, pipeline
placeholders, provider differences, credentials, certificate Secret/controller
and practitioner equivalence remain explicit review items.

The shared 79-check contract covers selected literals/structures and policy
values. Resource presence and subnet literal presence are weaker than evaluated
Terraform plan/deployment equivalence. The broader contract deliberately detects
the archived generator's contradictory criterion bounds and hard-coded network.
Do not revise historical observations to match renderer fixes.

Authoritative size inputs are this directory's Terraform/policy sources PLUS the
five Kustomize source files. Exclude README, rendered artifacts, tool code and
generated Python applications. Whole-DSL size contains additional features;
no fair-scope reduction percentage is reported before equivalence review.
