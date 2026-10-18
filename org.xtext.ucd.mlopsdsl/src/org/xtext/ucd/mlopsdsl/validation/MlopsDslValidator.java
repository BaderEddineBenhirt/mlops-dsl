package org.xtext.ucd.mlopsdsl.validation;

import mlops.AnnotationSpec;
import mlops.ComputeProfile;
import mlops.DataAsset;
import mlops.DecisionCriterion;
import mlops.DecisionPolicy;
import mlops.ExposureKind;
import mlops.InfraPlan;
import mlops.MlopsPackage;
import mlops.OperationalPlan;
import mlops.Resource;
import mlops.ServingKind;
import mlops.SplitSpec;

import org.eclipse.xtext.validation.Check;

/**
 * Xtext editor validation rules.
 *
 * These checks provide immediate error markers in the DSL editor.
 * Generator-time validation remains implemented in MlopsGenerator.
 */
public class MlopsDslValidator extends AbstractMlopsDslValidator {

    public static final String INVALID_CONFIGURATION =
            "invalidConfiguration";

    @Check
    public void checkGpuCoherence(OperationalPlan plan) {
        if (plan == null) {
            return;
        }

        boolean gpuProfile =
                plan.getCompute() == ComputeProfile.GPU_SINGLE
                || plan.getCompute() == ComputeProfile.GPU_MULTI;

        if (gpuProfile && !hasGpuResource(plan)) {
            error(
                "GPU compute profile requires at least one GPU resource.",
                plan,
                MlopsPackage.Literals.OPERATIONAL_PLAN__COMPUTE,
                INVALID_CONFIGURATION
            );
        }
    }

    @Check
    public void checkDataSplits(DataAsset data) {
        if (data == null || data.getSplits() == null
                || data.getSplits().isEmpty()) {
            return;
        }

        double sum = 0.0;

        for (SplitSpec split : data.getSplits()) {
            if (split == null) {
                continue;
            }

            double ratio = split.getRatio();
            sum += ratio;

            if (ratio <= 0.0 || ratio > 1.0) {
                error(
                    "A dataset split ratio must be greater than 0 and at most 1.",
                    split,
                    MlopsPackage.Literals.SPLIT_SPEC__RATIO,
                    INVALID_CONFIGURATION
                );
            }
        }

        if (Math.abs(sum - 1.0) > 0.0001) {
            error(
                "Dataset split ratios must sum to 1.0; current value is "
                    + sum + ".",
                data,
                MlopsPackage.Literals.DATA_ASSET__SPLITS,
                INVALID_CONFIGURATION
            );
        }
    }

    @Check
    public void checkPublicNetwork(InfraPlan infra) {
        if (infra == null || infra.getNetwork() == null) {
            return;
        }

        if (infra.getNetwork().getExposure() == ExposureKind.PUBLIC) {
            if (!Boolean.TRUE.equals(infra.getNetwork().getTls())) {
                error(
                    "Public network exposure requires TLS.",
                    infra,
                    MlopsPackage.Literals.INFRA_PLAN__NETWORK,
                    INVALID_CONFIGURATION
                );
            }

            if (isBlank(infra.getNetwork().getDomain())) {
                error(
                    "Public network exposure requires a domain.",
                    infra,
                    MlopsPackage.Literals.INFRA_PLAN__NETWORK,
                    INVALID_CONFIGURATION
                );
            }
        }
    }

    @Check
    public void checkServingPort(mlops.ServingSpec serving) {
        if (serving == null || serving.getKind() != ServingKind.API) {
            return;
        }

        if (serving.getPort() == null
                || serving.getPort() < 1
                || serving.getPort() > 65535) {
            error(
                "API serving requires a port between 1 and 65535.",
                serving,
                MlopsPackage.Literals.SERVING_SPEC__PORT,
                INVALID_CONFIGURATION
            );
        }
    }

    @Check
    public void checkDecisionCriteria(DecisionPolicy policy) {
        if (policy == null || policy.getCriteria() == null
                || policy.getCriteria().isEmpty()) {
            return;
        }

        double weightSum = 0.0;

        for (DecisionCriterion criterion : policy.getCriteria()) {
            if (criterion == null) {
                continue;
            }

            double weight = criterion.getWeight();
            weightSum += weight;

            if (weight <= 0.0) {
                error(
                    "Decision criterion weights must be greater than zero.",
                    criterion,
                    MlopsPackage.Literals.DECISION_CRITERION__WEIGHT,
                    INVALID_CONFIGURATION
                );
            }

            if (criterion.getMinValue() != null
                    && criterion.getMaxValue() != null
                    && criterion.getMinValue()
                        > criterion.getMaxValue()) {
                error(
                    "Decision criterion minValue cannot exceed maxValue.",
                    criterion,
                    MlopsPackage.Literals.DECISION_CRITERION__MIN_VALUE,
                    INVALID_CONFIGURATION
                );
            }
        }

        if (Math.abs(weightSum - 1.0) > 0.0001) {
            error(
                "Decision criterion weights must sum to 1.0; current value is "
                    + weightSum + ".",
                policy,
                MlopsPackage.Literals.DECISION_POLICY__CRITERIA,
                INVALID_CONFIGURATION
            );
        }
    }

    private boolean hasGpuResource(OperationalPlan plan) {
        if (plan.getResources() == null) {
            return false;
        }

        for (Resource resource : plan.getResources()) {
            if (resource != null
                    && "gpu".equalsIgnoreCase(resource.getKey())
                    && resource.getAmount() >= 1) {
                return true;
            }
        }

        return false;
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}