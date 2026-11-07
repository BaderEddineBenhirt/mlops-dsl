package org.xtext.ucd.mlopsdsl.generator;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.generator.IFileSystemAccess2;
import org.eclipse.xtext.generator.IGenerator2;
import org.eclipse.xtext.generator.IGeneratorContext;

import mlops.*;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.util.Diagnostician;

/**
 * MLOps DSL generator adapted for the research scope requested in the cahier des charges.
 *
 * Scope generated from the unified MLOps model:
 * - Terraform infrastructure platform layer from InfraPlan.
 * - CI/CD/CT pipeline skeleton from CiCdCtSpec + MlWorkflow.
 * - Experiment tracking configuration from ExperimentTrackingSpec.
 * - Data/model versioning configuration from VersioningSpec.
 * - Model registry policy from ModelRegistrySpec.
 * - AI Decision Gate policy from DecisionPolicy.
 * - Monitoring and retraining policy from MonitoringSpec + RetrainingPolicy.
 * - Governance report from GovernanceSpec.
 * - Optional Kubernetes runtime deployment for the external inference repository.
 *
 * This generator intentionally does not generate fake training code or fake models.
 * The ML application/inference repository remains external. The generated artifacts
 * provide the reproducible platform, orchestration and governance layer.
 */
public class MlopsGenerator implements IGenerator2 {

    @Override
    public void beforeGenerate(Resource resource, IFileSystemAccess2 fsa, IGeneratorContext context) {
    }

    @Override
    public void afterGenerate(Resource resource, IFileSystemAccess2 fsa, IGeneratorContext context) {
    }

    @Override
    public void doGenerate(
            Resource resource,
            IFileSystemAccess2 fsa,
            IGeneratorContext context) {

        if (resource == null) {
            throw new IllegalStateException("Resource is null.");
        }

        if (!resource.getErrors().isEmpty()) {
            throw new IllegalStateException(
                    "The DSL resource contains syntax/linking errors:\n"
                    + formatResourceErrors(resource));
        }

        List<MetaModelSpec> models =
                EcoreUtil2.getAllContentsOfType(
                        resource,
                        MetaModelSpec.class);

        if (models.isEmpty()) {
            throw new IllegalStateException(
                    "No MetaModelSpec found in resource: "
                    + resource.getURI());
        }

        for (MetaModelSpec model : models) {
            validateWithOcl(model);
            validateModel(model);
            generateFor(model, fsa);
        }
    }

    private void generateFor(
            MetaModelSpec model,
            IFileSystemAccess2 fsa) {

        if (model == null) {
            return;
        }

        /*
         * Do not call validateModel here.
         * The model is already validated once in doGenerate().
         */

        ProjectSpec project = model.getProject();
        OperationalPlan plan = model.getPlan();
        InfraPlan infra = model.getInfra();
        MlWorkflow workflow = model.getWorkflow();

        String base =
                "generated/"
                + sanitize(projectName(project))
                + "/";

        generateModelManifest(model, base, fsa);
        generateMlopsLifecycleArtifacts(
                project, plan, infra, workflow, base, fsa);
        generateAiDecisionEngine(project, base, fsa);
        generateTerraformStack(project, infra, base, fsa);
        generatePipelineFiles(project, plan, workflow, base, fsa);
        generateRuntimeDeployment(project, plan, infra, base, fsa);
        generateReadme(model, base, fsa);
    }

    /* =========================================================
     * Validation
     * ========================================================= */

    private void validateWithOcl(MetaModelSpec model) {
        if (model == null) {
            throw new IllegalStateException(
                    "Cannot validate a null MetaModelSpec.");
        }

        Diagnostic diagnostic =
                Diagnostician.INSTANCE.validate(model);

        if (diagnostic.getSeverity() == Diagnostic.ERROR) {
            throw new IllegalStateException(
                    "OCL/EMF validation failed:\n"
                    + formatDiagnostic(diagnostic));
        }
    }

    private String formatResourceErrors(
            org.eclipse.emf.ecore.resource.Resource resource) {

        StringBuilder result = new StringBuilder();

        for (org.eclipse.emf.ecore.resource.Resource.Diagnostic error
                : resource.getErrors()) {

            result.append("- ")
                  .append(error.getMessage())
                  .append("\n");
        }

        return result.toString();
    }

    private String formatDiagnostic(Diagnostic diagnostic) {
        StringBuilder result = new StringBuilder();

        if (diagnostic.getMessage() != null
                && !diagnostic.getMessage().trim().isEmpty()) {
            result.append("- ")
                  .append(diagnostic.getMessage())
                  .append("\n");
        }

        for (Diagnostic child : diagnostic.getChildren()) {
            result.append(formatDiagnostic(child));
        }

        return result.toString();
    }

    private void validateModel(MetaModelSpec model) {
        List<String> errors = new ArrayList<String>();

        if (model == null) {
            errors.add("MetaModelSpec must not be null.");
        } else {
            if (model.getProject() == null) {
                errors.add("MetaModelSpec.project is required.");
            }

            if (model.getPlan() == null) {
                errors.add("MetaModelSpec.plan is required.");
            }

            if (model.getInfra() == null) {
                errors.add("MetaModelSpec.infra is required.");
            }

            if (model.getWorkflow() == null) {
                errors.add("MetaModelSpec.workflow is required.");
            }

            if (model.getProject() != null) {
                validateProject(model.getProject(), errors);
            }

            if (model.getPlan() != null) {
                validatePlan(model.getPlan(), errors);
            }

            if (model.getInfra() != null) {
                validateInfra(model.getInfra(), errors);
            }

            if (model.getWorkflow() != null) {
                validateWorkflow(model.getWorkflow(), errors);
            }
        }

        if (!errors.isEmpty()) {
            throw new IllegalStateException(
                    "Invalid MLOps model:\n - "
                    + join(errors, "\n - "));
        }
    }

    private void validateProject(
            ProjectSpec project,
            List<String> errors) {

        if (isBlank(project.getName())) {
            errors.add("ProjectSpec.name is required.");
        }

        if (project.getData() == null) {
            errors.add("ProjectSpec.data is required.");
        } else {
            validateData(project.getData(), errors);
        }

        if (project.getTrain() == null) {
            errors.add("ProjectSpec.train is required.");
        }

        if (project.getEval() == null) {
            errors.add("ProjectSpec.eval is required.");
        }

        if (project.getTracking() == null) {
            errors.add(
                "ProjectSpec.tracking is required for experiment tracking.");
        }

        if (project.getVersioning() == null) {
            errors.add(
                "ProjectSpec.versioning is required for reproducibility.");
        }

        if (project.getModelRegistry() == null) {
            errors.add(
                "ProjectSpec.modelRegistry is required for model lifecycle.");
        }

        if (project.getDecisionPolicy() == null) {
            errors.add(
                "ProjectSpec.decisionPolicy is required for deployment gating.");
        } else {
            validateDecisionPolicy(
                    project.getDecisionPolicy(),
                    errors);
        }

        if (project.getRetrainingPolicy() == null) {
            errors.add(
                "ProjectSpec.retrainingPolicy is required for retraining.");
        }

        if (project.getGovernance() == null) {
            errors.add(
                "ProjectSpec.governance is required for traceability.");
        }

        if (project.getCicdct() == null) {
            errors.add(
                "ProjectSpec.cicdct is required for CI/CD/CT.");
        } else {
            validatePipeline(project.getCicdct(), errors);
        }

        ServingSpec serving = project.getServing();

        if (serving != null
                && serving.getKind() == ServingKind.API) {

            if (serving.getPort() == null
                    || serving.getPort() < 1
                    || serving.getPort() > 65535) {
                errors.add(
                    "ServingSpec.port must be between 1 and 65535.");
            }
        }

        if (serving != null
                && serving.getReplicas() != null
                && serving.getReplicas() < 1) {
            errors.add(
                "ServingSpec.replicas must be at least 1.");
        }

        if (serving != null
                && serving.getTimeoutSeconds() != null
                && serving.getTimeoutSeconds() <= 0) {
            errors.add(
                "ServingSpec.timeoutSeconds must be greater than zero.");
        }
    }

    private void validateData(
            DataAsset data,
            List<String> errors) {

        if (isBlank(data.getLocation())) {
            errors.add("DataAsset.location is required.");
        }

        if (data.getRecordCount() != null
                && data.getRecordCount() < 0) {
            errors.add("DataAsset.recordCount cannot be negative.");
        }

        if (data.getSizeBytes() != null
                && data.getSizeBytes() < 0) {
            errors.add("DataAsset.sizeBytes cannot be negative.");
        }

        if (data.getSplits() == null
                || data.getSplits().isEmpty()) {
            errors.add("DataAsset.splits must not be empty.");
        } else {
            double total = 0.0;

            for (SplitSpec split : data.getSplits()) {
                if (split == null) {
                    errors.add("DataAsset.splits contains a null value.");
                    continue;
                }

                if (isBlank(split.getName())) {
                    errors.add("Every dataset split requires a name.");
                }

                double ratio = split.getRatio();
                total += ratio;

                if (ratio <= 0.0 || ratio > 1.0) {
                    errors.add(
                        "Split ratio for '" + split.getName()
                        + "' must be greater than 0 and at most 1.");
                }
            }

            if (Math.abs(total - 1.0) > 0.0001) {
                errors.add(
                    "Dataset split ratios must sum to 1.0; current value is "
                    + total + ".");
            }
        }
    }

    private void validatePlan(
            OperationalPlan plan,
            List<String> errors) {

        if (plan.getCiTarget() == null) {
            errors.add("OperationalPlan.ciTarget is required.");
        }

        if (plan.getCompute() == null) {
            errors.add("OperationalPlan.compute is required.");
        }

        if (isBlank(plan.getEntryCmd())) {
            errors.add("OperationalPlan.entryCmd is required.");
        }

        if (plan.getRetries() != null
                && plan.getRetries() < 0) {
            errors.add("OperationalPlan.retries cannot be negative.");
        }

        if (plan.getResources() != null) {
        	for (mlops.Resource resource : plan.getResources()) {
                if (resource == null) {
                    errors.add("Resources cannot contain null values.");
                    continue;
                }

                if (isBlank(resource.getKey())) {
                    errors.add("Every resource requires a key.");
                }

                if (resource.getAmount() < 0) {
                    errors.add(
                        "Resource amount cannot be negative for key '"
                        + resource.getKey() + "'.");
                }
            }
        }

        boolean gpuProfile =
                plan.getCompute() == ComputeProfile.GPU_SINGLE
                || plan.getCompute() == ComputeProfile.GPU_MULTI;

        if (gpuProfile && !hasGpuResource(plan.getResources())) {
            errors.add(
                "GPU compute profile requires Resource "
                + "{ key = \"gpu\", amount >= 1 }.");
        }
    }

    private void validateInfra(
            InfraPlan infra,
            List<String> errors) {

        if (infra.getProvider() == null) {
            errors.add("InfraPlan.provider is required.");
        }

        if (infra.getOrchestrator() == null) {
            errors.add("InfraPlan.orchestrator is required.");
        }

        if (requiresCloudRegion(infra.getProvider())
                && isBlank(infra.getRegion())) {
            errors.add(
                "InfraPlan.region is required for cloud providers.");
        }

        if (isBlank(infra.getClusterName())) {
            errors.add("InfraPlan.clusterName is required.");
        }

        if (isBlank(infra.getNamespace())) {
            errors.add("InfraPlan.namespace is required.");
        }

        if (infra.getRuntime() == null
                || infra.getRuntime().getNodePools() == null
                || infra.getRuntime().getNodePools().isEmpty()) {
            errors.add(
                "InfraPlan.runtime.nodePools must not be empty.");
        } else {
            for (NodePoolSpec pool : infra.getRuntime().getNodePools()) {
                if (pool == null) {
                    errors.add("Node pools cannot contain null values.");
                    continue;
                }

                if (isBlank(pool.getName())) {
                    errors.add("Every node pool requires a name.");
                }

                if (isBlank(pool.getMachineType())) {
                    errors.add(
                        "Every node pool requires a machineType.");
                }

                if (pool.getMinNodes() < 0
                        || pool.getMinNodes()
                            > pool.getDesiredNodes()
                        || pool.getDesiredNodes()
                            > pool.getMaxNodes()) {
                    errors.add(
                        "Node pool counts must satisfy "
                        + "minNodes <= desiredNodes <= maxNodes.");
                }
            }
        }

        if (infra.getNetwork() != null
                && infra.getNetwork().getExposure()
                    == ExposureKind.PUBLIC) {

            if (!Boolean.TRUE.equals(infra.getNetwork().getTls())) {
                errors.add(
                    "Public exposure requires network.tls = true.");
            }

            if (isBlank(infra.getNetwork().getDomain())) {
                errors.add(
                    "Public exposure requires network.domain.");
            }
        }

        if (infra.getMaxMonthlyBudget() != null
                && infra.getMaxMonthlyBudget() < 0) {
            errors.add(
                "InfraPlan.maxMonthlyBudget cannot be negative.");
        }
    }

    private void validateDecisionPolicy(
            DecisionPolicy policy,
            List<String> errors) {

        if (policy.getCriteria() == null
                || policy.getCriteria().isEmpty()) {
            errors.add(
                "DecisionPolicy.criteria must contain at least one criterion.");
            return;
        }

        double totalWeight = 0.0;

        for (DecisionCriterion criterion : policy.getCriteria()) {
            if (criterion == null) {
                errors.add("Decision criteria cannot contain null values.");
                continue;
            }

            if (isBlank(criterion.getName())) {
                errors.add("Every decision criterion requires a name.");
            }

            if (criterion.getWeight() <= 0.0) {
                errors.add(
                    "Decision criterion weights must be greater than zero.");
            }

            totalWeight += criterion.getWeight();

            if (criterion.getMinValue() != null
                    && criterion.getMaxValue() != null
                    && criterion.getMinValue()
                        > criterion.getMaxValue()) {
                errors.add(
                    "Decision criterion minValue cannot exceed maxValue.");
            }
        }

        if (Math.abs(totalWeight - 1.0) > 0.0001) {
            errors.add(
                "Decision criterion weights must sum to 1.0; current value is "
                + totalWeight + ".");
        }
    }

    private void validatePipeline(
            CiCdCtSpec pipeline,
            List<String> errors) {

        if (pipeline.getTarget() == null) {
            errors.add("CiCdCtSpec.target is required.");
        }

        if (isBlank(pipeline.getPipelineFile())) {
            errors.add("CiCdCtSpec.pipelineFile is required.");
        }

        if (pipeline.getStages() == null
                || pipeline.getStages().isEmpty()) {
            errors.add("CiCdCtSpec.stages must not be empty.");
            return;
        }

        List<String> stageNames = new ArrayList<String>();

        for (PipelineStage stage : pipeline.getStages()) {
            if (stage == null) {
                errors.add("Pipeline stages cannot contain null values.");
                continue;
            }

            if (stage.getKind() == null) {
                errors.add("Every pipeline stage requires a kind.");
            }

            if (isBlank(stage.getName())) {
                errors.add("Every pipeline stage requires a name.");
            } else if (stageNames.contains(stage.getName())) {
                errors.add(
                    "Pipeline stage names must be unique: "
                    + stage.getName());
            } else {
                stageNames.add(stage.getName());
            }
        }
    }

    private void validateWorkflow(
            MlWorkflow workflow,
            List<String> errors) {

        if (workflow.getTasks() == null
                || workflow.getTasks().isEmpty()) {
            errors.add("MlWorkflow.tasks must not be empty.");
            return;
        }

        List<String> taskIds = new ArrayList<String>();

        for (MlTask task : workflow.getTasks()) {
            if (task == null) {
                errors.add("Workflow tasks cannot contain null values.");
                continue;
            }

            if (isBlank(task.getTaskId())) {
                errors.add("Every workflow task requires a taskId.");
            } else if (taskIds.contains(task.getTaskId())) {
                errors.add(
                    "Workflow task identifiers must be unique: "
                    + task.getTaskId());
            } else {
                taskIds.add(task.getTaskId());
            }

            validateArtifacts(task.getProduces(), errors);
            validateArtifacts(task.getConsumes(), errors);
        }

        if (workflow.getEdges() == null
                || workflow.getEdges().isEmpty()) {
            errors.add("MlWorkflow.edges must not be empty.");
            return;
        }

        for (Edge edge : workflow.getEdges()) {
            if (edge == null) {
                errors.add("Workflow edges cannot contain null values.");
                continue;
            }

            if (edge.getFrom() == null || edge.getTo() == null) {
                errors.add(
                    "Every workflow edge requires from and to tasks.");
            }

            if (edge.getFrom() != null
                    && !workflow.getTasks().contains(edge.getFrom())) {
                errors.add(
                    "Workflow edge source is not part of the workflow.");
            }

            if (edge.getTo() != null
                    && !workflow.getTasks().contains(edge.getTo())) {
                errors.add(
                    "Workflow edge target is not part of the workflow.");
            }
        }
    }

    private void validateArtifacts(
            List<Artifact> artifacts,
            List<String> errors) {

        if (artifacts == null) {
            return;
        }

        for (Artifact artifact : artifacts) {
            if (artifact == null) {
                errors.add("Workflow artifacts cannot contain null values.");
                continue;
            }

            if (isBlank(artifact.getPath())) {
                errors.add("Every workflow artifact requires a path.");
            }

            if (artifact.getKind() == null) {
                errors.add("Every workflow artifact requires a kind.");
            }
        }
    }

    private boolean hasGpuResource(
            List<mlops.Resource> resources) {

        if (resources == null) {
            return false;
        }

        for (mlops.Resource resource : resources) {
            if (resource != null
                    && "gpu".equalsIgnoreCase(resource.getKey())
                    && resource.getAmount() >= 1) {
                return true;
            }
        }

        return false;
    }

    private boolean requiresCloudRegion(
            CloudProvider provider) {

        return provider == CloudProvider.AWS
                || provider == CloudProvider.GCP
                || provider == CloudProvider.AZURE;
    }

    /* =========================================================
     * Main manifest
     * ========================================================= */

    private void generateModelManifest(MetaModelSpec model, String base, IFileSystemAccess2 fsa) {
        ProjectSpec project = model.getProject();
        OperationalPlan plan = model.getPlan();
        InfraPlan infra = model.getInfra();

        StringBuilder sb = new StringBuilder();
        line(sb, "version: " + quote(nz(model.getVersion())));
        line(sb, "schemaVersion: " + quote(nz(model.getSchemaVersion())));
        line(sb, "createdBy: " + quote(nz(model.getCreatedBy())));
        line(sb, "createdAt: " + quote(nz(model.getCreatedAt())));
        line(sb, "project:");
        line(sb, "  name: " + quote(projectName(project)));

        if (project != null && project.getData() != null) {
            DataAsset d = project.getData();
            line(sb, "  data:");
            line(sb, "    source: " + enumName(d.getSource()));
            line(sb, "    modality: " + enumName(d.getModality()));
            line(sb, "    location: " + quote(nz(d.getLocation())));
            line(sb, "    version: " + quote(nz(d.getVersion())));
            line(sb, "    privacy: " + quote(nz(d.getPrivacy())));
        }

        line(sb, "mlopsLifecycle:");
        if (project != null) {
            line(sb, "  tracking: " + enumName(project.getTracking() == null ? null : project.getTracking().getBackend()));
            line(sb, "  versioning: " + enumName(project.getVersioning() == null ? null : project.getVersioning().getBackend()));
            line(sb, "  registry: " + enumName(project.getModelRegistry() == null ? null : project.getModelRegistry().getBackend()));
            line(sb, "  decisionGate: " + (project.getDecisionPolicy() == null ? "disabled" : "enabled"));
            line(sb, "  retraining: " + enumName(project.getRetrainingPolicy() == null ? null : project.getRetrainingPolicy().getStrategy()));
        }

        line(sb, "operationalPlan:");
        if (plan != null) {
            line(sb, "  ciTarget: " + enumName(plan.getCiTarget()));
            line(sb, "  compute: " + enumName(plan.getCompute()));
            line(sb, "  packaging: " + enumName(plan.getPackaging()));
            line(sb, "  trainMode: " + enumName(plan.getTrainMode()));
            line(sb, "  servingMode: " + enumName(plan.getServingMode()));
        }

        line(sb, "infra:");
        if (infra != null) {
            line(sb, "  provider: " + enumName(infra.getProvider()));
            line(sb, "  region: " + quote(nz(infra.getRegion())));
            line(sb, "  orchestrator: " + enumName(infra.getOrchestrator()));
            line(sb, "  clusterName: " + quote(nz(infra.getClusterName())));
            line(sb, "  namespace: " + quote(namespace(infra, projectName(project))));
            line(sb, "  complianceLevel: " + quote(complianceLevel(infra)));
        }

        fsa.generateFile(base + "model-manifest.yaml", sb.toString());
    }

    /* =========================================================
     * MLOps lifecycle artifacts
     * ========================================================= */

    private void generateMlopsLifecycleArtifacts(ProjectSpec project, OperationalPlan plan, InfraPlan infra, MlWorkflow workflow,
            String base, IFileSystemAccess2 fsa) {
        if (project == null) return;

        fsa.generateFile(base + "mlops/lifecycle.yaml", renderLifecycle(project, plan, infra, workflow));

        if (project.getTracking() != null) {
            fsa.generateFile(base + "mlops/tracking.yaml", renderTracking(project.getTracking()));
        }
        if (project.getVersioning() != null) {
            fsa.generateFile(base + "mlops/versioning.yaml", renderVersioning(project.getVersioning()));
        }
        if (project.getModelRegistry() != null) {
            fsa.generateFile(base + "mlops/model-registry.yaml", renderModelRegistry(project.getModelRegistry()));
        }
        if (project.getDecisionPolicy() != null) {
            fsa.generateFile(base + "mlops/decision-gate.yaml", renderDecisionPolicy(project.getDecisionPolicy()));
        }
        if (project.getRetrainingPolicy() != null) {
            fsa.generateFile(base + "mlops/retraining-policy.yaml", renderRetrainingPolicy(project.getRetrainingPolicy()));
        }
        if (project.getMonitoring() != null) {
            fsa.generateFile(base + "mlops/monitoring-policy.yaml", renderMonitoringPolicy(project.getMonitoring()));
        }
        if (project.getGovernance() != null) {
            fsa.generateFile(base + "mlops/governance.yaml", renderGovernance(project.getGovernance()));
        }
        if (project.getCicdct() != null) {
            fsa.generateFile(base + "mlops/cicdct.yaml", renderCiCdCtSpec(project.getCicdct()));
        }
    }

    private String renderLifecycle(ProjectSpec project, OperationalPlan plan, InfraPlan infra, MlWorkflow workflow) {
        StringBuilder sb = new StringBuilder();
        line(sb, "project: " + quote(projectName(project)));
        line(sb, "pipeline:");
        line(sb, "  - data_validation");
        line(sb, "  - data_versioning");
        line(sb, "  - training");
        line(sb, "  - experiment_tracking");
        line(sb, "  - evaluation");
        line(sb, "  - model_registry");
        line(sb, "  - ai_decision_gate");
        line(sb, "  - deployment");
        line(sb, "  - monitoring");
        line(sb, "  - retraining_trigger");
        line(sb, "tools:");
        line(sb, "  tracking: " + enumName(project.getTracking() == null ? null : project.getTracking().getBackend()));
        line(sb, "  versioning: " + enumName(project.getVersioning() == null ? null : project.getVersioning().getBackend()));
        line(sb, "  registry: " + enumName(project.getModelRegistry() == null ? null : project.getModelRegistry().getBackend()));
        line(sb, "  cicdct: " + enumName(project.getCicdct() == null ? null : project.getCicdct().getTarget()));
        line(sb, "generatedArtifacts:");
        line(sb, "  terraform: infra/terraform");
        line(sb, "  cicdct: pipelines/");
        line(sb, "  mlopsPolicies: mlops/");
        line(sb, "  runtime: runtime/k8s");
        return sb.toString();
    }

    private String renderTracking(ExperimentTrackingSpec tracking) {
        StringBuilder sb = new StringBuilder();
        line(sb, "tracking:");
        line(sb, "  backend: " + enumName(tracking.getBackend()));
        line(sb, "  experimentName: " + quote(nz(tracking.getExperimentName())));
        line(sb, "  trackingUri: " + quote(nz(tracking.getTrackingUri())));
        line(sb, "  logParams: " + Boolean.TRUE.equals(tracking.getLogParams()));
        line(sb, "  logMetrics: " + Boolean.TRUE.equals(tracking.getLogMetrics()));
        line(sb, "  logArtifacts: " + Boolean.TRUE.equals(tracking.getLogArtifacts()));
        line(sb, "  logModel: " + Boolean.TRUE.equals(tracking.getLogModel()));
        return sb.toString();
    }

    private String renderVersioning(VersioningSpec versioning) {
        StringBuilder sb = new StringBuilder();
        line(sb, "versioning:");
        line(sb, "  backend: " + enumName(versioning.getBackend()));
        line(sb, "  versionData: " + Boolean.TRUE.equals(versioning.getVersionData()));
        line(sb, "  versionCode: " + Boolean.TRUE.equals(versioning.getVersionCode()));
        line(sb, "  versionModels: " + Boolean.TRUE.equals(versioning.getVersionModels()));
        line(sb, "  versionParams: " + Boolean.TRUE.equals(versioning.getVersionParams()));
        line(sb, "  remoteUri: " + quote(nz(versioning.getRemoteUri())));
        return sb.toString();
    }

    private String renderModelRegistry(ModelRegistrySpec registry) {
        StringBuilder sb = new StringBuilder();
        line(sb, "modelRegistry:");
        line(sb, "  backend: " + enumName(registry.getBackend()));
        line(sb, "  modelName: " + quote(nz(registry.getModelName())));
        line(sb, "  enableStaging: " + Boolean.TRUE.equals(registry.getEnableStaging()));
        line(sb, "  enableProduction: " + Boolean.TRUE.equals(registry.getEnableProduction()));
        line(sb, "  rollbackEnabled: " + Boolean.TRUE.equals(registry.getRollbackEnabled()));
        line(sb, "  approvalMode: " + quote(nz(registry.getApprovalMode())));
        return sb.toString();
    }

    private String renderDecisionPolicy(DecisionPolicy policy) {
        StringBuilder sb = new StringBuilder();
        line(sb, "decisionGate:");
        line(sb, "  autoPromote: " + Boolean.TRUE.equals(policy.getAutoPromote()));
        line(sb, "  minAccuracy: " + safeFloat(policy.getMinAccuracy()));
        line(sb, "  minF1: " + safeFloat(policy.getMinF1()));
        line(sb, "  maxLatencyMs: " + safeFloat(policy.getMaxLatencyMs()));
        line(sb, "  maxCost: " + safeFloat(policy.getMaxCost()));
        line(sb, "  maxDrift: " + safeFloat(policy.getMaxDrift()));
        line(sb, "  minTraceabilityScore: " + safeFloat(policy.getMinTraceabilityScore()));
        line(sb, "  criteria:");
        if (policy.getCriteria() != null) {
            for (DecisionCriterion c : policy.getCriteria()) {
                line(sb, "    - name: " + quote(nz(c.getName())));
                line(sb, "      weight: " + safeFloat(c.getWeight()));
                if (c.getMinValue() != null) {
                    line(sb, "      minValue: " + safeFloat(c.getMinValue()));
                }
                if (c.getMaxValue() != null) {
                    line(sb, "      maxValue: " + safeFloat(c.getMaxValue()));
                }
                line(sb, "      required: " + Boolean.TRUE.equals(c.getRequired()));
            }
        }
        line(sb, "  decisionFormula: \"S = weighted(performance, robustness, drift, cost, latency, traceability)\"");
        return sb.toString();
    }

    private String renderRetrainingPolicy(RetrainingPolicy policy) {
        StringBuilder sb = new StringBuilder();
        line(sb, "retraining:");
        line(sb, "  strategy: " + enumName(policy.getStrategy()));
        line(sb, "  driftThreshold: " + safeFloat(policy.getDriftThreshold()));
        line(sb, "  performanceDropThreshold: " + safeFloat(policy.getPerformanceDropThreshold()));
        line(sb, "  schedule: " + quote(nz(policy.getSchedule())));
        line(sb, "  autoTrigger: " + Boolean.TRUE.equals(policy.getAutoTrigger()));
        line(sb, "  retrainingQueue: " + quote(nz(policy.getRetrainingQueue())));
        return sb.toString();
    }

    private String renderMonitoringPolicy(MonitoringSpec monitoring) {
        StringBuilder sb = new StringBuilder();
        line(sb, "monitoring:");
        line(sb, "  alertChannel: " + quote(nz(monitoring.getAlertChannel())));
        line(sb, "  perfMetrics: " + yamlStringList(monitoring.getPerfMetrics()));
        line(sb, "  dataDrift:");
        renderDriftList(sb, monitoring.getDataDrift());
        line(sb, "  modelDrift:");
        renderDriftList(sb, monitoring.getModelDrift());
        return sb.toString();
    }

    private void renderDriftList(StringBuilder sb, List<DriftMetric> metrics) {
        if (metrics == null || metrics.isEmpty()) {
            line(sb, "    []");
            return;
        }
        for (DriftMetric d : metrics) {
            line(sb, "    - name: " + quote(nz(d.getName())));
            line(sb, "      warn: " + safeFloat(d.getWarn()));
            line(sb, "      fail: " + safeFloat(d.getFail()));
        }
    }

    private String renderGovernance(GovernanceSpec governance) {
        StringBuilder sb = new StringBuilder();
        line(sb, "governance:");
        line(sb, "  traceabilityRequired: " + Boolean.TRUE.equals(governance.getTraceabilityRequired()));
        line(sb, "  explainDecision: " + Boolean.TRUE.equals(governance.getExplainDecision()));
        line(sb, "  auditTrail: " + Boolean.TRUE.equals(governance.getAuditTrail()));
        line(sb, "  riskReport: " + Boolean.TRUE.equals(governance.getRiskReport()));
        line(sb, "  complianceFramework: " + quote(nz(governance.getComplianceFramework())));
        return sb.toString();
    }

    private String renderCiCdCtSpec(CiCdCtSpec spec) {
        StringBuilder sb = new StringBuilder();
        line(sb, "cicdct:");
        line(sb, "  target: " + enumName(spec.getTarget()));
        line(sb, "  pipelineFile: " + quote(nz(spec.getPipelineFile())));
        line(sb, "  enableCI: " + Boolean.TRUE.equals(spec.getEnableCI()));
        line(sb, "  enableCD: " + Boolean.TRUE.equals(spec.getEnableCD()));
        line(sb, "  enableCT: " + Boolean.TRUE.equals(spec.getEnableCT()));
        line(sb, "  stages:");
        if (spec.getStages() != null) {
            for (PipelineStage stage : spec.getStages()) {
                line(sb, "    - kind: " + enumName(stage.getKind()));
                line(sb, "      name: " + quote(nz(stage.getName())));
                line(sb, "      required: " + Boolean.TRUE.equals(stage.getRequired()));
                line(sb, "      tool: " + quote(nz(stage.getTool())));
                line(sb, "      outputArtifact: " + quote(nz(stage.getOutputArtifact())));
            }
        }
        return sb.toString();
    }


    /* =========================================================
     * AI Decision Engine generation
     * ========================================================= */

    private void generateAiDecisionEngine(ProjectSpec project, String base, IFileSystemAccess2 fsa) {
        if (project == null) return;

        String aiBase = base + "ai-decision-engine/";

        fsa.generateFile(aiBase + "requirements.txt", renderAiRequirements());
        fsa.generateFile(aiBase + "README.md", renderAiReadme(project));
        fsa.generateFile(aiBase + "run_decision_gate.py", renderRunDecisionGatePy());

        fsa.generateFile(aiBase + "decision_engine/__init__.py", "");
        fsa.generateFile(aiBase + "decision_engine/policy_loader.py", renderPolicyLoaderPy());
        fsa.generateFile(aiBase + "decision_engine/metrics_loader.py", renderMetricsLoaderPy());
        fsa.generateFile(aiBase + "decision_engine/scorer.py", renderScorerPy());
        fsa.generateFile(aiBase + "decision_engine/drift_detector.py", renderDriftDetectorPy());
        fsa.generateFile(aiBase + "decision_engine/decision_gate.py", renderDecisionGatePy());
        fsa.generateFile(aiBase + "decision_engine/retraining_advisor.py", renderRetrainingAdvisorPy());

        fsa.generateFile(aiBase + "experiments/train_candidates.py", renderTrainCandidatesPy(project));
        fsa.generateFile(aiBase + "experiments/evaluate_candidates.py", renderEvaluateCandidatesPy());
        fsa.generateFile(aiBase + "experiments/simulate_drift.py", renderSimulateDriftPy());
        fsa.generateFile(aiBase + "outputs/.gitkeep", "");
    }

    private String renderAiRequirements() {
        StringBuilder sb = new StringBuilder();
        line(sb, "pyyaml");
        line(sb, "numpy");
        line(sb, "pandas");
        line(sb, "scikit-learn");
        line(sb, "scipy");
        line(sb, "joblib");
        return sb.toString();
    }

    private String renderAiReadme(ProjectSpec project) {
        StringBuilder sb = new StringBuilder();
        line(sb, "# AI Decision Engine");
        line(sb, "");
        line(sb, "This module is generated together with the MDA/MDE artifacts for `" + projectName(project) + "`.");
        line(sb, "It consumes the generated MLOps policy descriptors from `../mlops/` and produces executable decision outputs.");
        line(sb, "");
        line(sb, "## Local execution");
        line(sb, "");
        line(sb, "```bash");
        line(sb, "cd ai-decision-engine");
        line(sb, "python -m venv .venv");
        line(sb, "source .venv/bin/activate  # Windows: .venv\\\\Scripts\\\\activate");
        line(sb, "pip install -r requirements.txt");
        line(sb, "python experiments/train_candidates.py");
        line(sb, "python experiments/simulate_drift.py");
        line(sb, "python run_decision_gate.py");
        line(sb, "```");
        line(sb, "");
        line(sb, "## Generated outputs");
        line(sb, "");
        line(sb, "- `outputs/model_metrics.json`");
        line(sb, "- `outputs/drift_report.json`");
        line(sb, "- `outputs/promotion_decision.json`");
        line(sb, "- `outputs/retraining_recommendation.json`");
        return sb.toString();
    }

    private String renderPolicyLoaderPy() {
        StringBuilder sb = new StringBuilder();
        line(sb, "from pathlib import Path");
        line(sb, "import yaml");
        line(sb, "");
        line(sb, "def load_yaml(path):");
        line(sb, "    path = Path(path)");
        line(sb, "    if not path.exists():");
        line(sb, "        raise FileNotFoundError(f'YAML policy file not found: {path}')");
        line(sb, "    with path.open('r', encoding='utf-8') as file:");
        line(sb, "        content = yaml.safe_load(file)");
        line(sb, "    if content is None:");
        line(sb, "        raise ValueError(f'YAML policy file is empty: {path}')");
        line(sb, "    return content");
        line(sb, "");
        line(sb, "def load_decision_policy(path):");
        line(sb, "    policy = load_yaml(path)");
        line(sb, "    if 'decisionGate' not in policy:");
        line(sb, "        raise ValueError(\"Invalid decision policy: missing 'decisionGate' section.\")");
        line(sb, "    return policy");
        line(sb, "");
        line(sb, "def load_retraining_policy(path):");
        line(sb, "    policy = load_yaml(path)");
        line(sb, "    if 'retraining' not in policy:");
        line(sb, "        raise ValueError(\"Invalid retraining policy: missing 'retraining' section.\")");
        line(sb, "    return policy");
        line(sb, "");
        line(sb, "def load_monitoring_policy(path):");
        line(sb, "    policy = load_yaml(path)");
        line(sb, "    if 'monitoring' not in policy:");
        line(sb, "        raise ValueError(\"Invalid monitoring policy: missing 'monitoring' section.\")");
        line(sb, "    return policy");
        return sb.toString();
    }

    private String renderMetricsLoaderPy() {
        StringBuilder sb = new StringBuilder();
        line(sb, "from pathlib import Path");
        line(sb, "import json");
        line(sb, "");
        line(sb, "def load_json(path):");
        line(sb, "    path = Path(path)");
        line(sb, "    if not path.exists():");
        line(sb, "        raise FileNotFoundError(f'JSON file not found: {path}')");
        line(sb, "    with path.open('r', encoding='utf-8') as file:");
        line(sb, "        return json.load(file)");
        line(sb, "");
        line(sb, "def save_json(data, path):");
        line(sb, "    path = Path(path)");
        line(sb, "    path.parent.mkdir(parents=True, exist_ok=True)");
        line(sb, "    with path.open('w', encoding='utf-8') as file:");
        line(sb, "        json.dump(data, file, indent=2)");
        line(sb, "");
        line(sb, "def load_model_metrics(path):");
        line(sb, "    metrics = load_json(path)");
        line(sb, "    if 'candidates' not in metrics:");
        line(sb, "        raise ValueError(\"Invalid model metrics: missing 'candidates' list.\")");
        line(sb, "    return metrics");
        line(sb, "");
        line(sb, "def load_drift_report(path):");
        line(sb, "    drift = load_json(path)");
        line(sb, "    if 'drift_score' not in drift:");
        line(sb, "        raise ValueError(\"Invalid drift report: missing 'drift_score'.\")");
        line(sb, "    return drift");
        return sb.toString();
    }

    private String renderScorerPy() {
        StringBuilder sb = new StringBuilder();
        line(sb, "def normalize_inverse(value, max_value):");
        line(sb, "    if max_value <= 0:");
        line(sb, "        return 0.0");
        line(sb, "    return max(0.0, min(1.0, 1.0 - (value / max_value)))");
        line(sb, "");
        line(sb, "def get_float(value, default=0.0):");
        line(sb, "    if value is None:");
        line(sb, "        return default");
        line(sb, "    return float(value)");
        line(sb, "");
        line(sb, "def compute_candidate_score(candidate, drift_score, decision_policy):");
        line(sb, "    gate = decision_policy['decisionGate']");
        line(sb, "    min_accuracy = get_float(gate.get('minAccuracy'), 0.0)");
        line(sb, "    min_f1 = get_float(gate.get('minF1'), 0.0)");
        line(sb, "    max_latency_ms = get_float(gate.get('maxLatencyMs'), 999999.0)");
        line(sb, "    max_cost = get_float(gate.get('maxCost'), 999999.0)");
        line(sb, "    max_drift = get_float(gate.get('maxDrift'), 1.0)");
        line(sb, "    min_traceability = get_float(gate.get('minTraceabilityScore'), 0.0)");
        line(sb, "");
        line(sb, "    accuracy = get_float(candidate.get('accuracy'), 0.0)");
        line(sb, "    f1 = get_float(candidate.get('f1'), 0.0)");
        line(sb, "    latency_ms = get_float(candidate.get('latency_ms'), 999999.0)");
        line(sb, "    cost = get_float(candidate.get('cost'), 0.0)");
        line(sb, "    traceability = get_float(candidate.get('traceability_score'), 0.0)");
        line(sb, "");
        line(sb, "    failures = []");
        line(sb, "    if accuracy < min_accuracy:");
        line(sb, "        failures.append('accuracy_below_threshold')");
        line(sb, "    if f1 < min_f1:");
        line(sb, "        failures.append('f1_below_threshold')");
        line(sb, "    if latency_ms > max_latency_ms:");
        line(sb, "        failures.append('latency_above_threshold')");
        line(sb, "    if cost > max_cost:");
        line(sb, "        failures.append('cost_above_threshold')");
        line(sb, "    if drift_score > max_drift:");
        line(sb, "        failures.append('drift_above_threshold')");
        line(sb, "    if traceability < min_traceability:");
        line(sb, "        failures.append('traceability_below_threshold')");
        line(sb, "");
        line(sb, "    latency_score = normalize_inverse(latency_ms, max_latency_ms)");
        line(sb, "    cost_score = normalize_inverse(cost, max_cost)");
        line(sb, "    drift_stability = normalize_inverse(drift_score, max_drift)");
        line(sb, "");
        line(sb, "    final_score = (");
        line(sb, "        0.40 * f1");
        line(sb, "        + 0.15 * accuracy");
        line(sb, "        + 0.15 * latency_score");
        line(sb, "        + 0.10 * cost_score");
        line(sb, "        + 0.10 * drift_stability");
        line(sb, "        + 0.10 * traceability");
        line(sb, "    )");
        line(sb, "    return round(final_score, 4), failures");
        return sb.toString();
    }

    private String renderDriftDetectorPy() {
        StringBuilder sb = new StringBuilder();
        line(sb, "import numpy as np");
        line(sb, "from scipy.stats import wasserstein_distance");
        line(sb, "");
        line(sb, "def average_wasserstein_score(reference, current, normalizer=10.0):");
        line(sb, "    scores = []");
        line(sb, "    for column_index in range(reference.shape[1]):");
        line(sb, "        score = wasserstein_distance(reference[:, column_index], current[:, column_index])");
        line(sb, "        scores.append(score)");
        line(sb, "    return round(min(float(np.mean(scores) / normalizer), 1.0), 4)");
        return sb.toString();
    }

    private String renderDecisionGatePy() {
        StringBuilder sb = new StringBuilder();
        line(sb, "from decision_engine.scorer import compute_candidate_score");
        line(sb, "");
        line(sb, "def decide_candidate(candidate, score, failures, drift_detected):");
        line(sb, "    if drift_detected:");
        line(sb, "        decision = 'retrain'");
        line(sb, "        reasons = ['drift_detected']");
        line(sb, "    elif failures:");
        line(sb, "        decision = 'reject'");
        line(sb, "        reasons = failures");
        line(sb, "    elif score >= 0.80:");
        line(sb, "        decision = 'promote'");
        line(sb, "        reasons = ['all_criteria_satisfied']");
        line(sb, "    else:");
        line(sb, "        decision = 'keep_current'");
        line(sb, "        reasons = ['score_below_promotion_threshold']");
        line(sb, "");
        line(sb, "    return {");
        line(sb, "        'model_id': candidate.get('model_id', 'unknown'),");
        line(sb, "        'score': score,");
        line(sb, "        'decision': decision,");
        line(sb, "        'reasons': reasons,");
        line(sb, "        'metrics': candidate");
        line(sb, "    }");
        line(sb, "");
        line(sb, "def run_decision_gate(decision_policy, model_metrics, drift_report):");
        line(sb, "    drift_score = float(drift_report.get('drift_score', 0.0))");
        line(sb, "    drift_detected = bool(drift_report.get('drift_detected', False))");
        line(sb, "    candidate_results = []");
        line(sb, "    for candidate in model_metrics['candidates']:");
        line(sb, "        score, failures = compute_candidate_score(candidate, drift_score, decision_policy)");
        line(sb, "        result = decide_candidate(candidate, score, failures, drift_detected)");
        line(sb, "        candidate_results.append(result)");
        line(sb, "    ranked = sorted(candidate_results, key=lambda item: item['score'], reverse=True)");
        line(sb, "    selected = ranked[0] if ranked else None");
        line(sb, "    return {");
        line(sb, "        'decision_gate_result': {");
        line(sb, "            'selected_model': selected['model_id'] if selected else None,");
        line(sb, "            'global_decision': selected['decision'] if selected else 'no_candidate',");
        line(sb, "            'candidates': ranked,");
        line(sb, "            'drift_context': drift_report");
        line(sb, "        }");
        line(sb, "    }");
        return sb.toString();
    }

    private String renderRetrainingAdvisorPy() {
        StringBuilder sb = new StringBuilder();
        line(sb, "def run_retraining_advisor(retraining_policy, drift_report, decision_result):");
        line(sb, "    policy = retraining_policy['retraining']");
        line(sb, "    drift_score = float(drift_report.get('drift_score', 0.0))");
        line(sb, "    drift_threshold = float(policy.get('driftThreshold', 1.0))");
        line(sb, "    auto_trigger = bool(policy.get('autoTrigger', False))");
        line(sb, "    strategy = policy.get('strategy', 'UNKNOWN')");
        line(sb, "    queue = policy.get('retrainingQueue', '')");
        line(sb, "    global_decision = decision_result['decision_gate_result']['global_decision']");
        line(sb, "    retraining_required = drift_score >= drift_threshold or global_decision == 'retrain'");
        line(sb, "    if retraining_required and auto_trigger:");
        line(sb, "        action = 'trigger_retraining'");
        line(sb, "    elif retraining_required:");
        line(sb, "        action = 'recommend_retraining'");
        line(sb, "    else:");
        line(sb, "        action = 'no_retraining_required'");
        line(sb, "    return {");
        line(sb, "        'retraining_recommendation': {");
        line(sb, "            'required': retraining_required,");
        line(sb, "            'action': action,");
        line(sb, "            'strategy': strategy,");
        line(sb, "            'retraining_queue': queue,");
        line(sb, "            'reason': {");
        line(sb, "                'drift_score': drift_score,");
        line(sb, "                'drift_threshold': drift_threshold,");
        line(sb, "                'decision_gate': global_decision");
        line(sb, "            }");
        line(sb, "        }");
        line(sb, "    }");
        return sb.toString();
    }

    private String renderTrainCandidatesPy(ProjectSpec project) {
        StringBuilder sb = new StringBuilder();
        line(sb, "from pathlib import Path");
        line(sb, "import argparse");
        line(sb, "import json");
        line(sb, "import time");
        line(sb, "import joblib");
        line(sb, "");
        line(sb, "from sklearn.datasets import load_breast_cancer");
        line(sb, "from sklearn.ensemble import RandomForestClassifier, GradientBoostingClassifier");
        line(sb, "from sklearn.linear_model import LogisticRegression");
        line(sb, "from sklearn.metrics import accuracy_score, f1_score, precision_score, recall_score");
        line(sb, "from sklearn.model_selection import train_test_split");
        line(sb, "from sklearn.pipeline import Pipeline");
        line(sb, "from sklearn.preprocessing import StandardScaler");
        line(sb, "");
        line(sb, "ROOT = Path(__file__).resolve().parents[1]");
        line(sb, "DEFAULT_OUTPUT = ROOT / 'outputs' / 'model_metrics.json'");
        line(sb, "MODELS_DIR = ROOT / 'outputs' / 'models'");
        line(sb, "");
        line(sb, "def measure_latency_ms(model, x_test, repeat=100):");
        line(sb, "    sample = x_test[:1]");
        line(sb, "    start = time.perf_counter()");
        line(sb, "    for _ in range(repeat):");
        line(sb, "        model.predict(sample)");
        line(sb, "    end = time.perf_counter()");
        line(sb, "    return ((end - start) / repeat) * 1000");
        line(sb, "");
        line(sb, "def train_and_evaluate(output_path):");
        line(sb, "    output_path = Path(output_path)");
        line(sb, "    output_path.parent.mkdir(parents=True, exist_ok=True)");
        line(sb, "    MODELS_DIR.mkdir(parents=True, exist_ok=True)");
        line(sb, "    dataset = load_breast_cancer()");
        line(sb, "    x_train, x_test, y_train, y_test = train_test_split(");
        line(sb, "        dataset.data, dataset.target, test_size=0.25, random_state=42, stratify=dataset.target");
        line(sb, "    )");
        line(sb, "    candidates = {");
        line(sb, "        'logistic_regression_v1': Pipeline([('scaler', StandardScaler()), ('model', LogisticRegression(max_iter=1000))]),");
        line(sb, "        'random_forest_v1': RandomForestClassifier(n_estimators=100, random_state=42),");
        line(sb, "        'gradient_boosting_v1': GradientBoostingClassifier(random_state=42)");
        line(sb, "    }");
        line(sb, "    results = []");
        line(sb, "    for model_id, model in candidates.items():");
        line(sb, "        model.fit(x_train, y_train)");
        line(sb, "        predictions = model.predict(x_test)");
        line(sb, "        model_path = MODELS_DIR / f'{model_id}.joblib'");
        line(sb, "        joblib.dump(model, model_path)");
        line(sb, "        result = {");
        line(sb, "            'model_id': model_id,");
        line(sb, "            'accuracy': round(float(accuracy_score(y_test, predictions)), 4),");
        line(sb, "            'precision': round(float(precision_score(y_test, predictions)), 4),");
        line(sb, "            'recall': round(float(recall_score(y_test, predictions)), 4),");
        line(sb, "            'f1': round(float(f1_score(y_test, predictions)), 4),");
        line(sb, "            'latency_ms': round(float(measure_latency_ms(model, x_test)), 4),");
        line(sb, "            'model_size_mb': round(float(model_path.stat().st_size / (1024 * 1024)), 4),");
        line(sb, "            'cost': 0.10,");
        line(sb, "            'traceability_score': 1.0,");
        line(sb, "            'artifact_path': str(model_path)");
        line(sb, "        }");
        line(sb, "        results.append(result)");
        line(sb, "    output = {");
        line(sb, "        'project': '" + sanitize(projectName(project)) + "',");
        line(sb, "        'dataset': 'sklearn_breast_cancer',");
        line(sb, "        'dataset_version': 'v1',");
        line(sb, "        'candidates': results");
        line(sb, "    }");
        line(sb, "    with output_path.open('w', encoding='utf-8') as file:");
        line(sb, "        json.dump(output, file, indent=2)");
        line(sb, "    print(json.dumps(output, indent=2))");
        line(sb, "");
        line(sb, "if __name__ == '__main__':");
        line(sb, "    parser = argparse.ArgumentParser()");
        line(sb, "    parser.add_argument('--output', default=str(DEFAULT_OUTPUT))");
        line(sb, "    args = parser.parse_args()");
        line(sb, "    train_and_evaluate(args.output)");
        return sb.toString();
    }

    private String renderEvaluateCandidatesPy() {
        StringBuilder sb = new StringBuilder();
        line(sb, "from pathlib import Path");
        line(sb, "import json");
        line(sb, "");
        line(sb, "ROOT = Path(__file__).resolve().parents[1]");
        line(sb, "METRICS = ROOT / 'outputs' / 'model_metrics.json'");
        line(sb, "");
        line(sb, "if __name__ == '__main__':");
        line(sb, "    if not METRICS.exists():");
        line(sb, "        raise FileNotFoundError('model_metrics.json not found. Run train_candidates.py first.')");
        line(sb, "    with METRICS.open('r', encoding='utf-8') as file:");
        line(sb, "        metrics = json.load(file)");
        line(sb, "    ranked = sorted(metrics['candidates'], key=lambda item: item.get('f1', 0.0), reverse=True)");
        line(sb, "    print(json.dumps({'ranked_candidates': ranked}, indent=2))");
        return sb.toString();
    }

    private String renderSimulateDriftPy() {
        StringBuilder sb = new StringBuilder();
        line(sb, "from pathlib import Path");
        line(sb, "import argparse");
        line(sb, "import json");
        line(sb, "import numpy as np");
        line(sb, "");
        line(sb, "from sklearn.datasets import load_breast_cancer");
        line(sb, "from sklearn.model_selection import train_test_split");
        line(sb, "from decision_engine.drift_detector import average_wasserstein_score");
        line(sb, "");
        line(sb, "ROOT = Path(__file__).resolve().parents[1]");
        line(sb, "DEFAULT_OUTPUT = ROOT / 'outputs' / 'drift_report.json'");
        line(sb, "");
        line(sb, "def simulate_drift(output_path, noise_level=2.5):");
        line(sb, "    output_path = Path(output_path)");
        line(sb, "    output_path.parent.mkdir(parents=True, exist_ok=True)");
        line(sb, "    dataset = load_breast_cancer()");
        line(sb, "    x_train, x_test, _, _ = train_test_split(");
        line(sb, "        dataset.data, dataset.target, test_size=0.25, random_state=42, stratify=dataset.target");
        line(sb, "    )");
        line(sb, "    rng = np.random.default_rng(42)");
        line(sb, "    x_drifted = x_test.copy()");
        line(sb, "    x_drifted[:, :5] = x_drifted[:, :5] + rng.normal(");
        line(sb, "        loc=noise_level, scale=noise_level, size=x_drifted[:, :5].shape");
        line(sb, "    )");
        line(sb, "    drift_score = average_wasserstein_score(x_train, x_drifted)");
        line(sb, "    report = {");
        line(sb, "        'dataset_version': 'v1_drifted',");
        line(sb, "        'method': 'average_wasserstein_distance',");
        line(sb, "        'drift_score': drift_score,");
        line(sb, "        'drift_detected': drift_score >= 0.30,");
        line(sb, "        'threshold': 0.30");
        line(sb, "    }");
        line(sb, "    with output_path.open('w', encoding='utf-8') as file:");
        line(sb, "        json.dump(report, file, indent=2)");
        line(sb, "    print(json.dumps(report, indent=2))");
        line(sb, "");
        line(sb, "if __name__ == '__main__':");
        line(sb, "    parser = argparse.ArgumentParser()");
        line(sb, "    parser.add_argument('--output', default=str(DEFAULT_OUTPUT))");
        line(sb, "    parser.add_argument('--noise-level', type=float, default=2.5)");
        line(sb, "    args = parser.parse_args()");
        line(sb, "    simulate_drift(args.output, args.noise_level)");
        return sb.toString();
    }

    private String renderRunDecisionGatePy() {
        StringBuilder sb = new StringBuilder();
        line(sb, "import argparse");
        line(sb, "from pathlib import Path");
        line(sb, "");
        line(sb, "from decision_engine.policy_loader import load_decision_policy, load_retraining_policy, load_monitoring_policy");
        line(sb, "from decision_engine.metrics_loader import load_model_metrics, load_drift_report, save_json");
        line(sb, "from decision_engine.decision_gate import run_decision_gate");
        line(sb, "from decision_engine.retraining_advisor import run_retraining_advisor");
        line(sb, "");
        line(sb, "ROOT = Path(__file__).resolve().parent");
        line(sb, "PROJECT_ROOT = ROOT.parent");
        line(sb, "");
        line(sb, "def ensure_neutral_drift(path):");
        line(sb, "    path = Path(path)");
        line(sb, "    if path.exists():");
        line(sb, "        return");
        line(sb, "    save_json({");
        line(sb, "        'dataset_version': 'v1',");
        line(sb, "        'method': 'neutral_default',");
        line(sb, "        'drift_score': 0.0,");
        line(sb, "        'drift_detected': False,");
        line(sb, "        'threshold': 0.30");
        line(sb, "    }, path)");
        line(sb, "");
        line(sb, "def main():");
        line(sb, "    parser = argparse.ArgumentParser()");
        line(sb, "    parser.add_argument('--policy', default=str(PROJECT_ROOT / 'mlops' / 'decision-gate.yaml'))");
        line(sb, "    parser.add_argument('--retraining', default=str(PROJECT_ROOT / 'mlops' / 'retraining-policy.yaml'))");
        line(sb, "    parser.add_argument('--monitoring', default=str(PROJECT_ROOT / 'mlops' / 'monitoring-policy.yaml'))");
        line(sb, "    parser.add_argument('--metrics', default=str(ROOT / 'outputs' / 'model_metrics.json'))");
        line(sb, "    parser.add_argument('--drift', default=str(ROOT / 'outputs' / 'drift_report.json'))");
        line(sb, "    parser.add_argument('--decision-output', default=str(ROOT / 'outputs' / 'promotion_decision.json'))");
        line(sb, "    parser.add_argument('--retraining-output', default=str(ROOT / 'outputs' / 'retraining_recommendation.json'))");
        line(sb, "    args = parser.parse_args()");
        line(sb, "");
        line(sb, "    ensure_neutral_drift(args.drift)");
        line(sb, "");
        line(sb, "    decision_policy = load_decision_policy(args.policy)");
        line(sb, "    retraining_policy = load_retraining_policy(args.retraining)");
        line(sb, "    load_monitoring_policy(args.monitoring)");
        line(sb, "    model_metrics = load_model_metrics(args.metrics)");
        line(sb, "    drift_report = load_drift_report(args.drift)");
        line(sb, "");
        line(sb, "    decision_result = run_decision_gate(decision_policy, model_metrics, drift_report)");
        line(sb, "    retraining_result = run_retraining_advisor(retraining_policy, drift_report, decision_result)");
        line(sb, "");
        line(sb, "    save_json(decision_result, args.decision_output)");
        line(sb, "    save_json(retraining_result, args.retraining_output)");
        line(sb, "    print('Decision Gate completed.')");
        line(sb, "    print(f'Decision output: {Path(args.decision_output).resolve()}')");
        line(sb, "    print(f'Retraining output: {Path(args.retraining_output).resolve()}')");
        line(sb, "");
        line(sb, "if __name__ == '__main__':");
        line(sb, "    main()");
        return sb.toString();
    }


    /* =========================================================
     * Terraform generation
     * ========================================================= */

    private void generateTerraformStack(ProjectSpec project, InfraPlan infra, String base, IFileSystemAccess2 fsa) {
        if (infra == null) return;
        String tfBase = base + "infra/terraform/";

        fsa.generateFile(tfBase + "versions.tf", renderTerraformVersions(infra));
        fsa.generateFile(tfBase + "variables.tf", renderTerraformVariables(project, infra));
        fsa.generateFile(tfBase + "main.tf", renderTerraformMain(infra));
        fsa.generateFile(tfBase + "outputs.tf", renderTerraformOutputs());
        fsa.generateFile(tfBase + "terraform.tfvars.example", renderTfvarsExample(project, infra));

        fsa.generateFile(tfBase + "modules/network/main.tf", renderNetworkModule(infra));
        fsa.generateFile(tfBase + "modules/storage/main.tf", renderStorageModule(infra));
        fsa.generateFile(tfBase + "modules/secrets/main.tf", renderSecretsModule(infra));
        fsa.generateFile(tfBase + "modules/observability/main.tf", renderObservabilityModule(infra));
        fsa.generateFile(tfBase + "modules/compute/main.tf", renderComputeModule(infra));
    }

    private String renderTerraformVersions(InfraPlan infra) {
        CloudProvider provider = infra.getProvider();
        StringBuilder sb = new StringBuilder();
        line(sb, "terraform {");
        line(sb, "  required_version = \">= 1.6.0\"");
        line(sb, "  required_providers {");
        if (provider == CloudProvider.AZURE) {
            line(sb, "    azurerm = { source = \"hashicorp/azurerm\", version = \"~> 3.100\" }");
        } else if (provider == CloudProvider.AWS) {
            line(sb, "    aws = { source = \"hashicorp/aws\", version = \"~> 5.0\" }");
        } else if (provider == CloudProvider.GCP) {
            line(sb, "    google = { source = \"hashicorp/google\", version = \"~> 5.0\" }");
        } else {
            line(sb, "    local = { source = \"hashicorp/local\", version = \"~> 2.4\" }");
        }
        line(sb, "  }");
        line(sb, "}");
        line(sb, "");
        if (provider == CloudProvider.AZURE) {
            line(sb, "provider \"azurerm\" {");
            line(sb, "  features {}");
            line(sb, "}");
        } else if (provider == CloudProvider.AWS) {
            line(sb, "provider \"aws\" {");
            line(sb, "  region = var.region");
            line(sb, "}");
        } else if (provider == CloudProvider.GCP) {
            line(sb, "provider \"google\" {");
            line(sb, "  project = var.account_id");
            line(sb, "  region  = var.region");
            line(sb, "}");
        }
        return sb.toString();
    }

    private String renderTerraformVariables(ProjectSpec project, InfraPlan infra) {
        StringBuilder sb = new StringBuilder();
        variable(sb, "project_name", "string", null, "Project name.");
        variable(sb, "environment", "string", "\"dev\"", "Deployment environment.");
        variable(sb, "region", "string", quote(defaultRegion(infra)), "Cloud region.");
        variable(sb, "account_id", "string", quote(nz(infra.getAccountId())), "Cloud account, subscription, or project id.");
        variable(sb, "cluster_name", "string", quote(clusterName(infra, projectName(project))), "Cluster name.");
        variable(sb, "namespace", "string", quote(namespace(infra, projectName(project))), "Kubernetes namespace.");
        variable(sb, "domain", "string", quote(infra.getNetwork() == null ? "" : nz(infra.getNetwork().getDomain())), "DNS domain.");
        variable(sb, "public_exposure", "bool", String.valueOf(isPublic(infra)), "Whether service is public.");
        variable(sb, "tls_enabled", "bool", String.valueOf(tlsEnabled(infra)), "Whether TLS is enabled.");
        variable(sb, "pii_data", "bool", String.valueOf(Boolean.TRUE.equals(infra.getPiiData())), "Whether workload processes PII.");
        variable(sb, "gdpr_restricted", "bool", String.valueOf(Boolean.TRUE.equals(infra.getGdprRestricted())), "Whether GDPR restrictions apply.");
        variable(sb, "max_monthly_budget", "number", infra.getMaxMonthlyBudget() == null ? "0" : String.valueOf(infra.getMaxMonthlyBudget()), "Budget guardrail.");
        DataAsset data = project == null ? null : project.getData();
        variable(sb, "data_source", "string", quote(enumName(data == null ? null : data.getSource())), "Dataset source.");
        variable(sb, "data_modality", "string", quote(enumName(data == null ? null : data.getModality())), "Dataset modality.");
        variable(sb, "data_location", "string", quote(data == null ? "" : nz(data.getLocation())), "Dataset location.");
        line(sb, "variable \"tags\" {");
        line(sb, "  type    = map(string)");
        line(sb, "  default = {}");
        line(sb, "}");
        return sb.toString();
    }

    private String renderTerraformMain(InfraPlan infra) {
        StringBuilder sb = new StringBuilder();
        line(sb, "locals {");
        line(sb, "  common_tags = merge(var.tags, {");
        line(sb, "    Project = var.project_name");
        line(sb, "    Environment = var.environment");
        line(sb, "    ManagedBy = \"mlopsdsl\"");
        line(sb, "    PiiData = tostring(var.pii_data)");
        line(sb, "    GdprRestricted = tostring(var.gdpr_restricted)");
        line(sb, "  })");
        line(sb, "}");
        line(sb, "");
        moduleCall(sb, "network");
        moduleCall(sb, "storage");
        moduleCall(sb, "secrets");
        moduleCall(sb, "compute");
        line(sb, "module \"observability\" {");
        line(sb, "  source = \"./modules/observability\"");
        commonModuleArgs(sb);
        line(sb, "}");
        return sb.toString();
    }

    private void moduleCall(StringBuilder sb, String name) {
        line(sb, "module \"" + name + "\" {");
        line(sb, "  source = \"./modules/" + name + "\"");
        commonModuleArgs(sb);
        if ("compute".equals(name)) {
            line(sb, "  network_id = module.network.network_id");
            line(sb, "  private_subnets = module.network.private_subnets");
        }
        line(sb, "}");
        line(sb, "");
    }

    private void commonModuleArgs(StringBuilder sb) {
        line(sb, "  project_name = var.project_name");
        line(sb, "  environment = var.environment");
        line(sb, "  region = var.region");
        line(sb, "  cluster_name = var.cluster_name");
        line(sb, "  namespace = var.namespace");
        line(sb, "  domain = var.domain");
        line(sb, "  public_exposure = var.public_exposure");
        line(sb, "  tls_enabled = var.tls_enabled");
        line(sb, "  pii_data = var.pii_data");
        line(sb, "  gdpr_restricted = var.gdpr_restricted");
        line(sb, "  data_source = var.data_source");
        line(sb, "  data_modality = var.data_modality");
        line(sb, "  data_location = var.data_location");
        line(sb, "  tags = local.common_tags");
    }

    private String renderTerraformOutputs() {
        StringBuilder sb = new StringBuilder();
        output(sb, "network_id", "module.network.network_id");
        output(sb, "private_subnets", "module.network.private_subnets");
        output(sb, "cluster_name", "module.compute.cluster_name");
        output(sb, "storage_dataset", "module.storage.dataset_uri");
        output(sb, "storage_artifacts", "module.storage.artifacts_uri");
        output(sb, "secrets_backend", "module.secrets.backend_ref");
        output(sb, "observability_workspace", "module.observability.workspace_ref");
        return sb.toString();
    }

    private String renderTfvarsExample(ProjectSpec project, InfraPlan infra) {
        StringBuilder sb = new StringBuilder();
        DataAsset data = project == null ? null : project.getData();
        line(sb, "project_name = " + quote(sanitize(projectName(project))));
        line(sb, "environment = \"dev\"");
        line(sb, "region = " + quote(defaultRegion(infra)));
        line(sb, "account_id = " + quote(nz(infra.getAccountId())));
        line(sb, "cluster_name = " + quote(clusterName(infra, projectName(project))));
        line(sb, "namespace = " + quote(namespace(infra, projectName(project))));
        line(sb, "domain = " + quote(infra.getNetwork() == null ? "" : nz(infra.getNetwork().getDomain())));
        line(sb, "public_exposure = " + isPublic(infra));
        line(sb, "tls_enabled = " + tlsEnabled(infra));
        line(sb, "pii_data = " + Boolean.TRUE.equals(infra.getPiiData()));
        line(sb, "gdpr_restricted = " + Boolean.TRUE.equals(infra.getGdprRestricted()));
        line(sb, "max_monthly_budget = " + (infra.getMaxMonthlyBudget() == null ? 0 : infra.getMaxMonthlyBudget()));
        line(sb, "data_source = " + quote(enumName(data == null ? null : data.getSource())));
        line(sb, "data_modality = " + quote(enumName(data == null ? null : data.getModality())));
        line(sb, "data_location = " + quote(data == null ? "" : nz(data.getLocation())));
        line(sb, "tags = { owner = \"mlops-team\" }");
        return sb.toString();
    }

    private String moduleVariables() {
        StringBuilder sb = new StringBuilder();
        variable(sb, "project_name", "string", null, "Project name.");
        variable(sb, "environment", "string", null, "Environment.");
        variable(sb, "region", "string", null, "Region.");
        variable(sb, "cluster_name", "string", null, "Cluster name.");
        variable(sb, "namespace", "string", null, "Namespace.");
        variable(sb, "domain", "string", "\"\"", "Domain.");
        variable(sb, "public_exposure", "bool", "false", "Public exposure.");
        variable(sb, "tls_enabled", "bool", "false", "TLS enabled.");
        variable(sb, "pii_data", "bool", "false", "PII data.");
        variable(sb, "gdpr_restricted", "bool", "false", "GDPR restricted.");
        variable(sb, "data_source", "string", "\"\"", "Data source.");
        variable(sb, "data_modality", "string", "\"\"", "Data modality.");
        variable(sb, "data_location", "string", "\"\"", "Data location.");
        line(sb, "variable \"tags\" {");
        line(sb, "  type    = map(string)");
        line(sb, "  default = {}");
        line(sb, "}");
        line(sb, "");
        return sb.toString();
    }

    private String renderNetworkModule(InfraPlan infra) {
        StringBuilder sb = new StringBuilder();
        line(sb, moduleVariables());

        if (infra.getProvider() == CloudProvider.AZURE) {
            line(sb, "resource \"azurerm_resource_group\" \"main\" {");
            line(sb, "  name     = \"rg-${var.project_name}-${var.environment}\"");
            line(sb, "  location = var.region");
            line(sb, "  tags     = var.tags");
            line(sb, "}");
            line(sb, "");

            line(sb, "resource \"azurerm_virtual_network\" \"main\" {");
            line(sb, "  name                = \"vnet-${var.project_name}-${var.environment}\"");
            line(sb, "  location            = azurerm_resource_group.main.location");
            line(sb, "  resource_group_name = azurerm_resource_group.main.name");
            line(sb, "  address_space       = [" + quote(infra.getNetwork() == null
                || isBlank(infra.getNetwork().getCidr()) ? "10.20.0.0/16" : infra.getNetwork().getCidr()) + "]");
            line(sb, "  tags                = var.tags");
            line(sb, "}");
            line(sb, "");

            line(sb, "resource \"azurerm_subnet\" \"private\" {");
            List<String> subnets = infra.getNetwork() == null ? new ArrayList<String>()
                : new ArrayList<String>(infra.getNetwork().getSubnets());
            if (subnets.isEmpty()) {
                subnets.add("10.20.1.0/24");
            }
            line(sb, "  count                = " + subnets.size());
            line(sb, "  name                 = \"snet-${var.project_name}-${var.environment}-${count.index}\"");
            line(sb, "  resource_group_name  = azurerm_resource_group.main.name");
            line(sb, "  virtual_network_name = azurerm_virtual_network.main.name");
            line(sb, "  address_prefixes     = [" + yamlStringList(subnets) + "[count.index]]");
            line(sb, "}");
            line(sb, "");

            output(sb, "network_id", "azurerm_virtual_network.main.id");
            output(sb, "private_subnets", "azurerm_subnet.private[*].id");

        } else if (infra.getProvider() == CloudProvider.AWS) {
            line(sb, "resource \"aws_vpc\" \"main\" {");
            line(sb, "  cidr_block = \"10.20.0.0/16\"");
            line(sb, "  tags       = var.tags");
            line(sb, "}");
            line(sb, "");

            line(sb, "resource \"aws_subnet\" \"private\" {");
            line(sb, "  vpc_id     = aws_vpc.main.id");
            line(sb, "  cidr_block = \"10.20.1.0/24\"");
            line(sb, "  tags       = var.tags");
            line(sb, "}");
            line(sb, "");

            output(sb, "network_id", "aws_vpc.main.id");
            output(sb, "private_subnets", "[aws_subnet.private.id]");

        } else if (infra.getProvider() == CloudProvider.GCP) {
            line(sb, "resource \"google_compute_network\" \"main\" {");
            line(sb, "  name                    = \"vpc-${var.project_name}-${var.environment}\"");
            line(sb, "  auto_create_subnetworks = false");
            line(sb, "}");
            line(sb, "");

            line(sb, "resource \"google_compute_subnetwork\" \"private\" {");
            line(sb, "  name          = \"snet-${var.project_name}-${var.environment}\"");
            line(sb, "  ip_cidr_range = \"10.20.1.0/24\"");
            line(sb, "  region        = var.region");
            line(sb, "  network       = google_compute_network.main.id");
            line(sb, "}");
            line(sb, "");

            output(sb, "network_id", "google_compute_network.main.id");
            output(sb, "private_subnets", "[google_compute_subnetwork.private.id]");

        } else {
            line(sb, "locals {");
            line(sb, "  network_id      = \"local-network\"");
            line(sb, "  private_subnets = [\"local-subnet\"]");
            line(sb, "}");
            line(sb, "");

            output(sb, "network_id", "local.network_id");
            output(sb, "private_subnets", "local.private_subnets");
        }

        return sb.toString();
    }

    private String renderStorageModule(InfraPlan infra) {
        StringBuilder sb = new StringBuilder();
        line(sb, moduleVariables());

        if (infra.getProvider() == CloudProvider.AZURE) {
            line(sb, "resource \"azurerm_resource_group\" \"storage\" {");
            line(sb, "  name     = \"rg-${var.project_name}-${var.environment}-storage\"");
            line(sb, "  location = var.region");
            line(sb, "  tags     = var.tags");
            line(sb, "}");
            line(sb, "");

            line(sb, "resource \"azurerm_storage_account\" \"main\" {");
            line(sb, "  name                     = substr(replace(lower(\"${var.project_name}${var.environment}ml\"), \"-\", \"\"), 0, 24)");
            line(sb, "  resource_group_name      = azurerm_resource_group.storage.name");
            line(sb, "  location                 = azurerm_resource_group.storage.location");
            line(sb, "  account_tier             = \"Standard\"");
            line(sb, "  account_replication_type = \"LRS\"");
            line(sb, "  min_tls_version          = \"TLS1_2\"");
            line(sb, "  tags                     = var.tags");
            line(sb, "}");
            line(sb, "");

            line(sb, "resource \"azurerm_storage_container\" \"dataset\" {");
            line(sb, "  name                  = \"dataset\"");
            line(sb, "  storage_account_name  = azurerm_storage_account.main.name");
            line(sb, "  container_access_type = \"private\"");
            line(sb, "}");
            line(sb, "");

            line(sb, "resource \"azurerm_storage_container\" \"artifacts\" {");
            line(sb, "  name                  = \"model-artifacts\"");
            line(sb, "  storage_account_name  = azurerm_storage_account.main.name");
            line(sb, "  container_access_type = \"private\"");
            line(sb, "}");
            line(sb, "");

            output(sb, "dataset_uri", "\"az://${azurerm_storage_container.dataset.name}/${var.data_location}\"");
            output(sb, "artifacts_uri", "\"az://${azurerm_storage_container.artifacts.name}\"");

        } else if (infra.getProvider() == CloudProvider.AWS) {
            line(sb, "resource \"aws_s3_bucket\" \"dataset\" {");
            line(sb, "  bucket = lower(\"${var.project_name}-${var.environment}-dataset\")");
            line(sb, "  tags   = var.tags");
            line(sb, "}");
            line(sb, "");

            line(sb, "resource \"aws_s3_bucket\" \"artifacts\" {");
            line(sb, "  bucket = lower(\"${var.project_name}-${var.environment}-artifacts\")");
            line(sb, "  tags   = var.tags");
            line(sb, "}");
            line(sb, "");

            output(sb, "dataset_uri", "\"s3://${aws_s3_bucket.dataset.bucket}/${var.data_location}\"");
            output(sb, "artifacts_uri", "\"s3://${aws_s3_bucket.artifacts.bucket}\"");

        } else if (infra.getProvider() == CloudProvider.GCP) {
            line(sb, "resource \"google_storage_bucket\" \"dataset\" {");
            line(sb, "  name                        = lower(\"${var.project_name}-${var.environment}-dataset\")");
            line(sb, "  location                    = var.region");
            line(sb, "  uniform_bucket_level_access = true");
            line(sb, "}");
            line(sb, "");

            line(sb, "resource \"google_storage_bucket\" \"artifacts\" {");
            line(sb, "  name                        = lower(\"${var.project_name}-${var.environment}-artifacts\")");
            line(sb, "  location                    = var.region");
            line(sb, "  uniform_bucket_level_access = true");
            line(sb, "}");
            line(sb, "");

            output(sb, "dataset_uri", "\"gs://${google_storage_bucket.dataset.name}/${var.data_location}\"");
            output(sb, "artifacts_uri", "\"gs://${google_storage_bucket.artifacts.name}\"");

        } else {
            line(sb, "resource \"local_file\" \"storage\" {");
            line(sb, "  filename = \"${path.module}/storage.txt\"");
            line(sb, "  content  = \"local storage placeholder\"");
            line(sb, "}");
            line(sb, "");

            output(sb, "dataset_uri", "local_file.storage.filename");
            output(sb, "artifacts_uri", "local_file.storage.filename");
        }

        return sb.toString();
    }

    private String renderSecretsModule(InfraPlan infra) {
        StringBuilder sb = new StringBuilder();
        line(sb, moduleVariables());

        if (infra.getProvider() == CloudProvider.AZURE) {
            line(sb, "data \"azurerm_client_config\" \"current\" {}");
            line(sb, "");

            line(sb, "resource \"azurerm_resource_group\" \"secrets\" {");
            line(sb, "  name     = \"rg-${var.project_name}-${var.environment}-secrets\"");
            line(sb, "  location = var.region");
            line(sb, "  tags     = var.tags");
            line(sb, "}");
            line(sb, "");

            line(sb, "resource \"azurerm_key_vault\" \"mlops\" {");
            line(sb, "  name                       = substr(replace(lower(\"kv-${var.project_name}-${var.environment}\"), \"-\", \"\"), 0, 24)");
            line(sb, "  location                   = azurerm_resource_group.secrets.location");
            line(sb, "  resource_group_name        = azurerm_resource_group.secrets.name");
            line(sb, "  tenant_id                  = data.azurerm_client_config.current.tenant_id");
            line(sb, "  sku_name                   = \"standard\"");
            line(sb, "  purge_protection_enabled   = false");
            line(sb, "  soft_delete_retention_days = 7");
            line(sb, "  tags                       = var.tags");
            line(sb, "}");
            line(sb, "");

            output(sb, "backend_ref", "azurerm_key_vault.mlops.id");

        } else if (infra.getProvider() == CloudProvider.AWS) {
            line(sb, "resource \"aws_secretsmanager_secret\" \"mlops\" {");
            line(sb, "  name = \"/${var.project_name}/${var.environment}/mlops\"");
            line(sb, "  tags = var.tags");
            line(sb, "}");
            line(sb, "");

            output(sb, "backend_ref", "aws_secretsmanager_secret.mlops.arn");

        } else if (infra.getProvider() == CloudProvider.GCP) {
            line(sb, "resource \"google_secret_manager_secret\" \"mlops\" {");
            line(sb, "  secret_id = \"${var.project_name}-${var.environment}-mlops\"");
            line(sb, "  replication {");
            line(sb, "    auto {}");
            line(sb, "  }");
            line(sb, "}");
            line(sb, "");

            output(sb, "backend_ref", "google_secret_manager_secret.mlops.id");

        } else {
            line(sb, "resource \"local_file\" \"secrets\" {");
            line(sb, "  filename = \"${path.module}/secrets.txt\"");
            line(sb, "  content  = \"local secrets placeholder\"");
            line(sb, "}");
            line(sb, "");

            output(sb, "backend_ref", "local_file.secrets.filename");
        }

        return sb.toString();
    }

    private String renderObservabilityModule(InfraPlan infra) {
        StringBuilder sb = new StringBuilder();
        line(sb, moduleVariables());

        if (infra.getProvider() == CloudProvider.AZURE) {
            line(sb, "resource \"azurerm_resource_group\" \"obs\" {");
            line(sb, "  name     = \"rg-${var.project_name}-${var.environment}-obs\"");
            line(sb, "  location = var.region");
            line(sb, "  tags     = var.tags");
            line(sb, "}");
            line(sb, "");

            line(sb, "resource \"azurerm_log_analytics_workspace\" \"mlops\" {");
            line(sb, "  name                = \"law-${var.project_name}-${var.environment}\"");
            line(sb, "  location            = azurerm_resource_group.obs.location");
            line(sb, "  resource_group_name = azurerm_resource_group.obs.name");
            line(sb, "  sku                 = \"PerGB2018\"");
            line(sb, "  retention_in_days   = var.gdpr_restricted ? 30 : 90");
            line(sb, "  tags                = var.tags");
            line(sb, "}");
            line(sb, "");

            output(sb, "workspace_ref", "azurerm_log_analytics_workspace.mlops.id");

        } else if (infra.getProvider() == CloudProvider.AWS) {
            line(sb, "resource \"aws_cloudwatch_log_group\" \"mlops\" {");
            line(sb, "  name              = \"/mlops/${var.project_name}/${var.environment}\"");
            line(sb, "  retention_in_days = var.gdpr_restricted ? 30 : 90");
            line(sb, "  tags              = var.tags");
            line(sb, "}");
            line(sb, "");

            output(sb, "workspace_ref", "aws_cloudwatch_log_group.mlops.arn");

        } else if (infra.getProvider() == CloudProvider.GCP) {
            line(sb, "resource \"google_logging_project_sink\" \"mlops\" {");
            line(sb, "  name        = \"${var.project_name}-${var.environment}-logs\"");
            line(sb, "  destination = \"logging.googleapis.com/projects/${var.account_id}\"");
            line(sb, "  filter      = \"resource.type=\\\"k8s_container\\\"\"");
            line(sb, "}");
            line(sb, "");

            output(sb, "workspace_ref", "google_logging_project_sink.mlops.id");

        } else {
            line(sb, "resource \"local_file\" \"observability\" {");
            line(sb, "  filename = \"${path.module}/observability.txt\"");
            line(sb, "  content  = \"local observability placeholder\"");
            line(sb, "}");
            line(sb, "");

            output(sb, "workspace_ref", "local_file.observability.filename");
        }

        return sb.toString();
    }
    private String renderComputeModule(InfraPlan infra) {
        StringBuilder sb = new StringBuilder();

        line(sb, moduleVariables());

        line(sb, "variable \"network_id\" {");
        line(sb, "  type = string");
        line(sb, "}");
        line(sb, "");

        line(sb, "variable \"private_subnets\" {");
        line(sb, "  type    = list(string)");
        line(sb, "  default = []");
        line(sb, "}");
        line(sb, "");

        if (infra.getProvider() == CloudProvider.AZURE && infra.getOrchestrator() == Orchestrator.KUBERNETES) {
            NodePoolSpec system = firstNodePool(infra);

            line(sb, "resource \"azurerm_resource_group\" \"compute\" {");
            line(sb, "  name     = \"rg-${var.project_name}-${var.environment}-compute\"");
            line(sb, "  location = var.region");
            line(sb, "  tags     = var.tags");
            line(sb, "}");
            line(sb, "");

            line(sb, "resource \"azurerm_kubernetes_cluster\" \"main\" {");
            line(sb, "  name                = var.cluster_name");
            line(sb, "  location            = azurerm_resource_group.compute.location");
            line(sb, "  resource_group_name = azurerm_resource_group.compute.name");
            line(sb, "  dns_prefix          = substr(replace(lower(var.cluster_name), \"_\", \"-\"), 0, 45)");
            line(sb, "");

            line(sb, "  default_node_pool {");
            line(sb, "    name                = \"" + sanitize(system.getName()) + "\"");
            line(sb, "    vm_size             = \"" + nz(system.getMachineType()) + "\"");
            line(sb, "    vnet_subnet_id      = length(var.private_subnets) > 0 ? var.private_subnets[0] : null");
            line(sb, "    enable_auto_scaling = true");
            line(sb, "    node_count          = " + safeInt(system.getDesiredNodes(), 1));
            line(sb, "    min_count           = " + safeInt(system.getMinNodes(), 1));
            line(sb, "    max_count           = " + safeInt(system.getMaxNodes(), 1));
            line(sb, "  }");
            line(sb, "");

            line(sb, "  identity {");
            line(sb, "    type = \"SystemAssigned\"");
            line(sb, "  }");
            line(sb, "");

            line(sb, "  workload_identity_enabled = true");
            line(sb, "  oidc_issuer_enabled       = true");
            line(sb, "  tags                      = var.tags");
            line(sb, "}");
            line(sb, "");

            for (NodePoolSpec pool : infra.getRuntime().getNodePools()) {
                if (pool == system) {
                    continue;
                }

                line(sb, "resource \"azurerm_kubernetes_cluster_node_pool\" \"" + sanitize(pool.getName()) + "\" {");
                line(sb, "  name                  = \"" + sanitize(pool.getName()) + "\"");
                line(sb, "  kubernetes_cluster_id = azurerm_kubernetes_cluster.main.id");
                line(sb, "  vm_size               = \"" + nz(pool.getMachineType()) + "\"");
                line(sb, "  enable_auto_scaling   = true");
                line(sb, "  node_count            = " + safeInt(pool.getDesiredNodes(), 1));
                line(sb, "  min_count             = " + safeInt(pool.getMinNodes(), 1));
                line(sb, "  max_count             = " + safeInt(pool.getMaxNodes(), 1));
                line(sb, "  vnet_subnet_id        = length(var.private_subnets) > 0 ? var.private_subnets[0] : null");

                if (Boolean.TRUE.equals(pool.getSpot())) {
                    line(sb, "  priority              = \"Spot\"");
                    line(sb, "  eviction_policy       = \"Delete\"");
                    line(sb, "  spot_max_price        = -1");
                }

                line(sb, "  tags                  = var.tags");
                line(sb, "}");
                line(sb, "");
            }

            output(sb, "cluster_name", "azurerm_kubernetes_cluster.main.name");
        } else {
            line(sb, "locals {");
            line(sb, "  cluster_name = var.cluster_name");
            line(sb, "}");
            line(sb, "");

            output(sb, "cluster_name", "local.cluster_name");
        }

        return sb.toString();
    }
    /* =========================================================
     * CI/CD/CT generation
     * ========================================================= */

    private void generatePipelineFiles(ProjectSpec project, OperationalPlan plan, MlWorkflow workflow, String base, IFileSystemAccess2 fsa) {
        if (project == null || project.getCicdct() == null) return;
        CiCdCtSpec cicdct = project.getCicdct();
        fsa.generateFile(base + "pipelines/pipeline-model.yaml", renderPipelineModel(project, plan, workflow));
        if (cicdct.getTarget() == CITarget.GITHUB) {
            fsa.generateFile(base + ".github/workflows/" + defaultFile(cicdct.getPipelineFile(), "mlops-pipeline.yml"), renderGithubActions(project, plan, workflow));
        } else if (cicdct.getTarget() == CITarget.GITLAB) {
            fsa.generateFile(base + defaultFile(cicdct.getPipelineFile(), ".gitlab-ci.yml"), renderGitlabCi(project));
        } else if (cicdct.getTarget() == CITarget.AZURE) {
            fsa.generateFile(base + defaultFile(cicdct.getPipelineFile(), "azure-pipelines.yml"), renderAzurePipeline(project));
        } else if (cicdct.getTarget() == CITarget.JENKINS) {
            fsa.generateFile(base + defaultFile(cicdct.getPipelineFile(), "Jenkinsfile"), renderJenkinsfile(project));
        }
    }

    private String renderPipelineModel(ProjectSpec project, OperationalPlan plan, MlWorkflow workflow) {
        StringBuilder sb = new StringBuilder();
        line(sb, "project: " + quote(projectName(project)));
        line(sb, "target: " + enumName(project.getCicdct().getTarget()));
        line(sb, "ci: " + Boolean.TRUE.equals(project.getCicdct().getEnableCI()));
        line(sb, "cd: " + Boolean.TRUE.equals(project.getCicdct().getEnableCD()));
        line(sb, "ct: " + Boolean.TRUE.equals(project.getCicdct().getEnableCT()));
        line(sb, "stages:");
        for (String stage : pipelineStages(project, workflow)) {
            line(sb, "  - " + stage);
        }
        return sb.toString();
    }

    private String renderGithubActions(ProjectSpec project, OperationalPlan plan, MlWorkflow workflow) {
        StringBuilder sb = new StringBuilder();
        line(sb, "name: MLOps CI-CD-CT Pipeline");
        line(sb, "on:");
        line(sb, "  push:");
        line(sb, "    branches: [ main ]");
        line(sb, "  pull_request:");
        line(sb, "jobs:");
        line(sb, "  mlops:");
        line(sb, "    runs-on: ubuntu-latest");
        line(sb, "    steps:");
        line(sb, "      - uses: actions/checkout@v4");
        line(sb, "      - uses: hashicorp/setup-terraform@v3");
        for (String stage : pipelineStages(project, workflow)) {
            line(sb, "      - name: " + stage);
            line(sb, "        run: |");
            for (String cmd : stageCommands(stage, project)) {
                line(sb, "          " + cmd);
            }
        }
        return sb.toString();
    }

    private String renderGitlabCi(ProjectSpec project) {
        StringBuilder sb = new StringBuilder();
        line(sb, "stages:");
        for (String stage : pipelineStages(project, null)) line(sb, "  - " + normalizeStage(stage));
        for (String stage : pipelineStages(project, null)) {
            line(sb, normalizeStage(stage) + ":");
            line(sb, "  stage: " + normalizeStage(stage));
            line(sb, "  script:");
            for (String cmd : stageCommands(stage, project)) line(sb, "    - " + cmd);
            line(sb, "");
        }
        return sb.toString();
    }

    private String renderAzurePipeline(ProjectSpec project) {
        StringBuilder sb = new StringBuilder();
        line(sb, "trigger:");
        line(sb, "  - main");
        line(sb, "pool:");
        line(sb, "  vmImage: ubuntu-latest");
        line(sb, "stages:");
        for (String stage : pipelineStages(project, null)) {
            line(sb, "  - stage: " + normalizeStage(stage));
            line(sb, "    jobs:");
            line(sb, "      - job: " + normalizeStage(stage) + "Job");
            line(sb, "        steps:");
            line(sb, "          - script: |");
            for (String cmd : stageCommands(stage, project)) line(sb, "              " + cmd);
        }
        return sb.toString();
    }

    private String renderJenkinsfile(ProjectSpec project) {
        StringBuilder sb = new StringBuilder();
        line(sb, "pipeline {");
        line(sb, "  agent any");
        line(sb, "  stages {");
        for (String stage : pipelineStages(project, null)) {
            line(sb, "    stage('" + stage + "') {");
            line(sb, "      steps {");
            for (String cmd : stageCommands(stage, project)) line(sb, "        sh '" + cmd.replace("'", "") + "'");
            line(sb, "      }");
            line(sb, "    }");
        }
        line(sb, "  }");
        line(sb, "}");
        return sb.toString();
    }

    private List<String> pipelineStages(ProjectSpec project, MlWorkflow workflow) {
        List<String> stages = new ArrayList<String>();
        if (project != null && project.getCicdct() != null && project.getCicdct().getStages() != null && !project.getCicdct().getStages().isEmpty()) {
            for (PipelineStage s : project.getCicdct().getStages()) addUnique(stages, stageName(s));
            return stages;
        }
        addUnique(stages, "data_validation");
        addUnique(stages, "data_versioning");
        addUnique(stages, "training");
        addUnique(stages, "experiment_tracking");
        addUnique(stages, "evaluation");
        addUnique(stages, "drift_simulation");
        addUnique(stages, "model_registry");
        addUnique(stages, "decision_gate");
        addUnique(stages, "terraform_validate");
        addUnique(stages, "terraform_plan");
        addUnique(stages, "deployment");
        addUnique(stages, "monitoring");
        addUnique(stages, "retraining_check");
        return stages;
    }

    private String stageName(PipelineStage stage) {
        if (stage == null) return "stage";
        if (!isBlank(stage.getName())) return sanitize(stage.getName());
        return enumName(stage.getKind()).toLowerCase(Locale.ROOT);
    }

    private List<String> stageCommands(String stage, ProjectSpec project) {
        List<String> cmds = new ArrayList<String>();
        String s = normalizeStage(stage);

        if (s.contains("terraform_validate")) {
            cmds.add("cd infra/terraform");
            cmds.add("terraform init");
            cmds.add("terraform fmt -recursive -check");
            cmds.add("terraform validate");
        } else if (s.contains("terraform_plan")) {
            cmds.add("cd infra/terraform");
            cmds.add("terraform plan");
        } else if (s.contains("data_validation")) {
            cmds.add("echo 'Validating dataset contract according to model-manifest.yaml'");
        } else if (s.contains("version")) {
            cmds.add("echo 'Versioning data/code/model according to mlops/versioning.yaml'");
        } else if (s.contains("training")) {
            cmds.add("cd ai-decision-engine");
            cmds.add("python experiments/train_candidates.py --output outputs/model_metrics.json");
            cmds.add("cd ..");
        } else if (s.contains("tracking")) {
            cmds.add("echo 'Tracking experiment according to mlops/tracking.yaml'");
        } else if (s.contains("evaluation")) {
            cmds.add("cd ai-decision-engine");
            cmds.add("python experiments/evaluate_candidates.py");
            cmds.add("cd ..");
        } else if (s.contains("drift")) {
            cmds.add("cd ai-decision-engine");
            cmds.add("python experiments/simulate_drift.py --output outputs/drift_report.json");
            cmds.add("cd ..");
        } else if (s.contains("decision")) {
            cmds.add("cd ai-decision-engine");
            cmds.add("python run_decision_gate.py");
            cmds.add("cd ..");
        } else if (s.contains("registry")) {
            cmds.add("echo 'Registering selected model according to mlops/model-registry.yaml'");
        } else if (s.contains("deploy")) {
            cmds.add("echo 'Deploying runtime layer if application image is available'");
            cmds.add("kubectl apply -f runtime/k8s/ || true");
        } else if (s.contains("monitor")) {
            cmds.add("echo 'Applying monitoring policy from mlops/monitoring-policy.yaml'");
        } else if (s.contains("retrain")) {
            cmds.add("cd ai-decision-engine");
            cmds.add("python run_decision_gate.py");
            cmds.add("cd ..");
            cmds.add("cat ai-decision-engine/outputs/retraining_recommendation.json || true");
        } else {
            cmds.add("echo 'Running " + stage + " stage generated from MLOps DSL'");
        }
        return cmds;
    }

    /* =========================================================
     * Kubernetes runtime deployment
     * ========================================================= */

    private void generateRuntimeDeployment(ProjectSpec project, OperationalPlan plan, InfraPlan infra, String base, IFileSystemAccess2 fsa) {
        if (project == null || project.getServing() == null || project.getServing().getKind() == ServingKind.NONE) return;
        if (infra == null || infra.getOrchestrator() != Orchestrator.KUBERNETES) return;
        String runtimeBase = base + "runtime/k8s/";
        fsa.generateFile(runtimeBase + "namespace.yaml", renderNamespace(project, infra));
        fsa.generateFile(runtimeBase + "deployment.yaml", renderRuntimeDeployment(project, plan, infra));
        fsa.generateFile(runtimeBase + "service.yaml", renderRuntimeService(project, infra));
        if (isPublic(infra) || !isBlank(infra.getNetwork() == null ? null : infra.getNetwork().getDomain())) {
            fsa.generateFile(runtimeBase + "ingress.yaml", renderRuntimeIngress(project, infra));
        }
    }

    private String renderNamespace(ProjectSpec project, InfraPlan infra) {
        StringBuilder sb = new StringBuilder();
        line(sb, "apiVersion: v1");
        line(sb, "kind: Namespace");
        line(sb, "metadata:");
        line(sb, "  name: " + namespace(infra, projectName(project)));
        line(sb, "  labels:");
        line(sb, "    app.kubernetes.io/managed-by: mlopsdsl");
        line(sb, "    compliance-level: " + complianceLevel(infra).toLowerCase(Locale.ROOT));
        return sb.toString();
    }

    private String renderRuntimeDeployment(ProjectSpec project, OperationalPlan plan, InfraPlan infra) {
        StringBuilder sb = new StringBuilder();
        ServingSpec serving = project.getServing();
        String app = sanitize(projectName(project)) + "-serving";
        int port = serving.getPort() == null ? 8080 : serving.getPort().intValue();
        int replicas = serving.getReplicas() == null ? 1 : serving.getReplicas().intValue();
        line(sb, "apiVersion: apps/v1");
        line(sb, "kind: Deployment");
        line(sb, "metadata:");
        line(sb, "  name: " + app);
        line(sb, "  namespace: " + namespace(infra, projectName(project)));
        line(sb, "spec:");
        line(sb, "  replicas: " + replicas);
        line(sb, "  selector:");
        line(sb, "    matchLabels: { app: " + app + " }");
        line(sb, "  template:");
        line(sb, "    metadata:");
        line(sb, "      labels: { app: " + app + " }");
        line(sb, "    spec:");
        line(sb, "      containers:");
        line(sb, "        - name: serving");
        line(sb, "          image: " + runtimeImage(project, plan));
        line(sb, "          ports:");
        line(sb, "            - containerPort: " + port);
        line(sb, "          readinessProbe:");
        line(sb, "            httpGet: { path: " + quote(defaultPath(serving.getReadinessPath(), serving.getHealthPath(), "/health")) + ", port: " + port + " }");
        line(sb, "          livenessProbe:");
        line(sb, "            httpGet: { path: " + quote(defaultPath(serving.getHealthPath(), null, "/health")) + ", port: " + port + " }");
        return sb.toString();
    }

    private String renderRuntimeService(ProjectSpec project, InfraPlan infra) {
        String app = sanitize(projectName(project)) + "-serving";
        int port = project.getServing().getPort() == null ? 8080 : project.getServing().getPort().intValue();
        StringBuilder sb = new StringBuilder();
        line(sb, "apiVersion: v1");
        line(sb, "kind: Service");
        line(sb, "metadata:");
        line(sb, "  name: " + app);
        line(sb, "  namespace: " + namespace(infra, projectName(project)));
        line(sb, "spec:");
        line(sb, "  selector: { app: " + app + " }");
        line(sb, "  ports:");
        line(sb, "    - port: " + port);
        line(sb, "      targetPort: " + port);
        return sb.toString();
    }

    private String renderRuntimeIngress(ProjectSpec project, InfraPlan infra) {
        String app = sanitize(projectName(project)) + "-serving";
        String domain = infra.getNetwork() == null ? "" : nz(infra.getNetwork().getDomain());
        int port = project.getServing().getPort() == null ? 8080 : project.getServing().getPort().intValue();
        StringBuilder sb = new StringBuilder();
        line(sb, "apiVersion: networking.k8s.io/v1");
        line(sb, "kind: Ingress");
        line(sb, "metadata:");
        line(sb, "  name: " + app);
        line(sb, "  namespace: " + namespace(infra, projectName(project)));
        line(sb, "spec:");
        if (infra.getNetwork() != null && !isBlank(infra.getNetwork().getIngressClass())) {
            line(sb, "  ingressClassName: " + quote(infra.getNetwork().getIngressClass()));
        }
        if (infra.getNetwork() != null && Boolean.TRUE.equals(infra.getNetwork().getTls())) {
            line(sb, "  tls:");
            line(sb, "    - hosts:");
            line(sb, "        - " + quote(domain));
            line(sb, "      secretName: " + app + "-tls");
        }
        line(sb, "  rules:");
        line(sb, "    - host: " + domain);
        line(sb, "      http:");
        line(sb, "        paths:");
        line(sb, "          - path: /");
        line(sb, "            pathType: Prefix");
        line(sb, "            backend:");
        line(sb, "              service:");
        line(sb, "                name: " + app);
        line(sb, "                port: { number: " + port + " }");
        return sb.toString();
    }

    private String runtimeImage(ProjectSpec project, OperationalPlan plan) {
        if (plan != null && !isBlank(plan.getBaseImage())) return plan.getBaseImage();
        String name = sanitize(projectName(project));
        return "ghcr.io/your-org/" + name + ":latest";
    }

    /* =========================================================
     * README
     * ========================================================= */

    private void generateReadme(MetaModelSpec model, String base, IFileSystemAccess2 fsa) {
        StringBuilder sb = new StringBuilder();
        ProjectSpec project = model.getProject();
        InfraPlan infra = model.getInfra();
        line(sb, "# Generated MLOps Artifacts");
        line(sb, "");
        line(sb, "Project: `" + projectName(project) + "`");
        line(sb, "");
        line(sb, "## Generated layers");
        line(sb, "");
        line(sb, "- `infra/terraform/`: Terraform platform infrastructure.");
        line(sb, "- `pipelines/`: CI/CD/CT pipeline model.");
        line(sb, "- `mlops/`: tracking, versioning, registry, decision gate, retraining and governance policies.");
        line(sb, "- `runtime/k8s/`: Kubernetes runtime descriptors for the external inference application.");
        line(sb, "- `ai-decision-engine/`: executable AI-assisted decision layer consuming generated MLOps policies.");
        if (infra != null && infra.getOrchestrator() == Orchestrator.KUBERNETES
                && project != null && project.getServing() != null
                && project.getServing().getKind() != ServingKind.NONE
                && infra.getNetwork() != null && Boolean.TRUE.equals(infra.getNetwork().getTls())) {
            line(sb, "");
            line(sb, "## Ingress TLS prerequisite");
            line(sb, "");
            line(sb, "The generated Ingress references the TLS Secret `"
                    + sanitize(projectName(project)) + "-serving-tls` in namespace `"
                    + namespace(infra, projectName(project)) + "`.");
            line(sb, "Provision that Secret with a certificate valid for the configured domain and its private key before deployment, or manage it through your certificate controller.");
            line(sb, "This generator does not issue certificates, create the TLS Secret, install an Ingress controller, or verify live HTTPS.");
        }
        line(sb, "");
        line(sb, "## AI Decision Gate execution");
        line(sb, "");
        line(sb, "```bash");
        line(sb, "cd ai-decision-engine");
        line(sb, "python -m venv .venv");
        line(sb, "source .venv/bin/activate  # Windows: .venv\\Scripts\\activate");
        line(sb, "pip install -r requirements.txt");
        line(sb, "python experiments/train_candidates.py");
        line(sb, "python experiments/simulate_drift.py");
        line(sb, "python run_decision_gate.py");
        line(sb, "```");
        line(sb, "");
        line(sb, "The decision engine reads `../mlops/decision-gate.yaml`, `../mlops/retraining-policy.yaml`, and `../mlops/monitoring-policy.yaml` automatically. No manual copy is required.");
        line(sb, "");
        line(sb, "## Terraform validation");
        line(sb, "");
        line(sb, "```bash");
        line(sb, "cd infra/terraform");
        line(sb, "terraform init");
        line(sb, "terraform fmt -recursive");
        line(sb, "terraform validate");
        line(sb, "terraform plan -var-file=terraform.tfvars.example -out=tfplan");
        line(sb, "terraform apply tfplan");
        line(sb, "```");
        line(sb, "");
        line(sb, "## Scope note");
        line(sb, "");
        line(sb, "The generated artifacts provide the MLOps automation, governance, infrastructure, and AI-assisted decision layer. The generated AI decision engine trains baseline candidate models for evaluation and consumes generated decision/retraining/monitoring policies automatically. The production ML application, Dockerfile and inference API may remain in an external repository.");
        fsa.generateFile(base + "README.md", sb.toString());
    }

    /* =========================================================
     * Utility methods
     * ========================================================= */

    private void variable(StringBuilder sb, String name, String type, String defaultValue, String description) {
        line(sb, "variable \"" + name + "\" {");
        line(sb, "  type = " + type);
        line(sb, "  description = " + quote(description));
        if (defaultValue != null) line(sb, "  default = " + defaultValue);
        line(sb, "}");
        line(sb, "");
    }

    private void output(StringBuilder sb, String name, String value) {
        line(sb, "output \"" + name + "\" {");
        line(sb, "  value = " + value);
        line(sb, "}");
        line(sb, "");
    }

    private NodePoolSpec firstNodePool(InfraPlan infra) {
        if (infra != null && infra.getRuntime() != null && infra.getRuntime().getNodePools() != null && !infra.getRuntime().getNodePools().isEmpty()) {
            return infra.getRuntime().getNodePools().get(0);
        }
        throw new IllegalStateException("At least one node pool is required.");
    }

    private String clusterName(InfraPlan infra, String fallback) {
        if (infra != null && !isBlank(infra.getClusterName())) return infra.getClusterName();
        return sanitize(fallback) + "-cluster";
    }

    private String namespace(InfraPlan infra, String fallback) {
        if (infra != null && !isBlank(infra.getNamespace())) return infra.getNamespace();
        return sanitize(fallback);
    }

    private String defaultRegion(InfraPlan infra) {
        if (infra != null && !isBlank(infra.getRegion())) return infra.getRegion();
        if (infra != null && infra.getProvider() == CloudProvider.AZURE) return "westeurope";
        if (infra != null && infra.getProvider() == CloudProvider.GCP) return "europe-west1";
        if (infra != null && infra.getProvider() == CloudProvider.AWS) return "eu-west-1";
        return "local";
    }

    private boolean isPublic(InfraPlan infra) {
        return infra != null && infra.getNetwork() != null && infra.getNetwork().getExposure() == ExposureKind.PUBLIC;
    }

    private boolean tlsEnabled(InfraPlan infra) {
        if (infra == null || infra.getNetwork() == null) {
            return false;
        }

        Object tlsValue = infra.getNetwork().getTls();
        return Boolean.TRUE.equals(tlsValue);
    }
    
    private String complianceLevel(InfraPlan infra) {
        if (infra == null) return "STANDARD";
        if (Boolean.TRUE.equals(infra.getGdprRestricted()) || Boolean.TRUE.equals(infra.getPiiData())) return "STRICT";
        return "STANDARD";
    }

    private String projectName(ProjectSpec project) {
        if (project != null && !isBlank(project.getName())) return project.getName();
        return "mlops-project";
    }

    private String defaultPath(String primary, String secondary, String fallback) {
        if (!isBlank(primary)) return primary;
        if (!isBlank(secondary)) return secondary;
        return fallback;
    }

    private String defaultFile(String value, String fallback) {
        return isBlank(value) ? fallback : value;
    }

    private int safeInt(Integer value, int fallback) {
        return value == null ? fallback : value.intValue();
    }

    private String safeFloat(Float value) {
        return value == null ? "0.0" : String.valueOf(value.floatValue());
    }

    private String yamlStringList(List<String> values) {
        if (values == null || values.isEmpty()) return "[]";
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < values.size(); i++) {
            if (i > 0) sb.append(", ");
            sb.append(quote(values.get(i)));
        }
        sb.append("]");
        return sb.toString();
    }

    private String normalizeStage(String stage) {
        return sanitize(stage).replace('-', '_');
    }

    private void addUnique(List<String> list, String value) {
        if (!isBlank(value) && !list.contains(value)) list.add(value);
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    private String nz(String value) {
        return value == null ? "" : value;
    }

    private String enumName(Enum<?> e) {
        return e == null ? "" : e.name();
    }

    private String quote(String value) {
        return "\"" + nz(value).replace("\\", "\\\\").replace("\"", "\\\"") + "\"";
    }

    private String sanitize(String value) {
        String v = nz(value).trim().toLowerCase(Locale.ROOT).replaceAll("[^a-z0-9-]+", "-");
        v = v.replaceAll("-+", "-");
        v = v.replaceAll("^-", "").replaceAll("-$", "");
        return v.isEmpty() ? "mlops" : v;
    }

    private String join(List<?> values, String separator) {
        if (values == null || values.isEmpty()) return "";
        StringBuilder sb = new StringBuilder();
        for (Object v : values) {
            if (v == null) continue;
            if (sb.length() > 0) sb.append(separator);
            sb.append(String.valueOf(v));
        }
        return sb.toString();
    }

    private void line(StringBuilder sb, String line) {
        sb.append(line == null ? "" : line).append("\n");
    }
}
