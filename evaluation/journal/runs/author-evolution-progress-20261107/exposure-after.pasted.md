**DataAsset** defects_ds_v1 {

&#x20; **source** = **IMAGE_FOLDER**

&#x20; **modality** = **IMAGE**

&#x20; **location** = "defects_ds_v1"

&#x20; **format** = "jpg"

&#x20; **schema** = "https\://storage.azure.com/schema/defects.json"

&#x20; **version** = "2025-01"

&#x20; **sizeBytes** = 2150000000

&#x20; **recordCount** = 120000

&#x20; **license** = "proprietary"

&#x20; **privacy** = "non_pii"

&#x20; **hash** = "sha256:vision-defects-v1"



&#x20; **splits** = [

&#x20;   **SplitSpec** { **name** = "train" **ratio** = 0.80 },

&#x20;   **SplitSpec** { **name** = "valid" **ratio** = 0.10 },

&#x20;   **SplitSpec** { **name** = "test" **ratio** = 0.10 }

&#x20; ]



&#x20; **annotations** = [

&#x20;   **AnnotationSpec** {

&#x20;     **labelSet** = "surface_defects_v1"

&#x20;     **guideline** = "bbox classification: scratch, crack, stain, deformation"

&#x20;   }

&#x20; ]

}



**FeatureSet** vision_features_v1 {

&#x20; **name** = "vision_features_v1"

}



**TrainingSpec** pytorch {

&#x20; **framework** = "pytorch"

&#x20; **algorithm** = "resnet50"



&#x20; **hyperparams** = [

&#x20;   **Param** { **key** = "epochs" **value** = "30" },

&#x20;   **Param** { **key** = "lr" **value** = "0.0005" },

&#x20;   **Param** { **key** = "batch" **value** = "64" },

&#x20;   **Param** { **key** = "optimizer" **value** = "adamw" }

&#x20; ]



&#x20; **image** = "mcr.microsoft.com/azureml/pytorch:2.1-cuda11.8"

&#x20; **command** = ["python","train.py"]



&#x20; **resources** = [

&#x20;   **Resource** { **key** = "GPU" **amount** = 1 },

&#x20;   **Resource** { **key** = "CPU" **amount** = 4 },

&#x20;   **Resource** { **key** = "memory" **amount** = 16384 }

&#x20; ]



&#x20; **env** = [

&#x20;   **Param** { **key** = "DATASET_PATH" **value** = "defects_ds_v1" },

&#x20;   **Param** { **key** = "MODEL_OUTPUT" **value** = "models/resnet.pt" }

&#x20; ]

}



**EvaluationSpec** accuracy {

&#x20; **metrics** = ["accuracy","precision","recall","f1","auc"]



&#x20; **thresholds** = [

&#x20;   **Threshold** { **key** = "accuracy" **value** = 0.90 **op** = **GE** },

&#x20;   **Threshold** { **key** = "f1" **value** = 0.88 **op** = **GE** }

&#x20; ]

}



**RegistrySpec** azureml {

&#x20; **kind** = "azureml"

&#x20; **registerAs** = "vision_defects_model_v1"



&#x20; **tags** = [

&#x20;   "vision",

&#x20;   "resnet50",

&#x20;   "azure",

&#x20;   "aks",

&#x20;   "industrial-qa",

&#x20;   "defect-detection",

&#x20;   "mlops"

&#x20; ]



&#x20; **description** = "Defects detection model for industrial quality assurance"

&#x20; **stage** = **STAGING**

}



**ServingSpec** azure {

&#x20; **kind** = **API**

&#x20; **port** = 8080

&#x20; **healthPath** = "/health"

&#x20; **readinessPath** = "/ready"

&#x20; **replicas** = 2

&#x20; **autoscaling** = **true**

&#x20; **timeoutSeconds** = 20



&#x20; **resources** = [

&#x20;   **Resource** { **key** = "CPU" **amount** = 2 },

&#x20;   **Resource** { **key** = "memory" **amount** = 4096 }

&#x20; ]



&#x20; **env** = [

&#x20;   **Param** { **key** = "MODEL_NAME" **value** = "vision_defects_model_v1" },

&#x20;   **Param** { **key** = "MODEL_STAGE" **value** = "staging" }

&#x20; ]



&#x20; **routes** = ["/predict","/health","/ready"]

}



**MonitoringSpec** monitoring_policy {

&#x20; **perfMetrics** = ["p95_latency_ms","p99_latency_ms","throughput_rps","error_rate"]



&#x20; **dataDrift** = [

&#x20;   **DriftMetric** { **name** = "image_brightness_shift" **warn** = 0.15 **fail** = 0.30 }

&#x20; ]



&#x20; **modelDrift** = [

&#x20;   **DriftMetric** { **name** = "accuracy_drop" **warn** = 0.05 **fail** = 0.10 }

&#x20; ]



&#x20; **alertChannel** = "slack://#azure-monitoring"

}



**ExperimentTrackingSpec** tracking_mlflow {

&#x20; **backend** = **MLFLOW**

&#x20; **experimentName** = "vision_defects_experiment"

&#x20; **trackingUri** = "http\://mlflow\.local:5000"

&#x20; **logParams** = **true**

&#x20; **logMetrics** = **true**

&#x20; **logArtifacts** = **true**

&#x20; **logModel** = **true**

}



**VersioningSpec** dvc_versioning {

&#x20; **backend** = **DVC**

&#x20; **versionData** = **true**

&#x20; **versionCode** = **true**

&#x20; **versionModels** = **true**

&#x20; **versionParams** = **true**

&#x20; **remoteUri** = "azure://vision-datasets/dvc"

}



**ModelRegistrySpec** model_registry {

&#x20; **backend** = **AZURE_ML**

&#x20; **modelName** = "vision_defects_model_v1"

&#x20; **enableStaging** = **true**

&#x20; **enableProduction** = **true**

&#x20; **rollbackEnabled** = **true**

&#x20; **approvalMode** = "automatic"

}



**DecisionPolicy** deployment_gate {

&#x20; **autoPromote** = **true**

&#x20; **minAccuracy** = 0.90

&#x20; **minF1** = 0.88

&#x20; **maxLatencyMs** = 200.0

&#x20; **maxCost** = 50.0

&#x20; **maxDrift** = 0.15

&#x20; **minTraceabilityScore** = 0.95



&#x20; **criteria** = [

&#x20;   **DecisionCriterion** {

&#x20;     **name** = "performance"

&#x20;     **weight** = 0.35

&#x20;     **minValue** = 0.90

&#x20;     **required** = **true**

&#x20;   },

&#x20;   **DecisionCriterion** {

&#x20;     **name** = "robustness"

&#x20;     **weight** = 0.20

&#x20;     **minValue** = 0.85

&#x20;     **required** = **true**

&#x20;   },

&#x20;   **DecisionCriterion** {

&#x20;     **name** = "drift_stability"

&#x20;     **weight** = 0.15

&#x20;     **maxValue** = 0.15

&#x20;     **required** = **true**

&#x20;   },

&#x20;   **DecisionCriterion** {

&#x20;     **name** = "latency"

&#x20;     **weight** = 0.10

&#x20;     **maxValue** = 200.0

&#x20;     **required** = **true**

&#x20;   },

&#x20;   **DecisionCriterion** {

&#x20;     **name** = "traceability"

&#x20;     **weight** = 0.20

&#x20;     **minValue** = 0.95

&#x20;     **required** = **true**

&#x20;   }

&#x20; ]

}



**RetrainingPolicy** drift_retraining {

&#x20; **strategy** = **HYBRID**

&#x20; **driftThreshold** = 0.15

&#x20; **performanceDropThreshold** = 0.08

&#x20; **schedule** = "weekly"

&#x20; **autoTrigger** = **true**

&#x20; **retrainingQueue** = "gpu-training"

}



**GovernanceSpec** governance_policy {

&#x20; **traceabilityRequired** = **true**

&#x20; **explainDecision** = **true**

&#x20; **auditTrail** = **true**

&#x20; **riskReport** = **true**

&#x20; **complianceFramework** = "NIST-AI-RMF"

}



**CiCdCtSpec** cicdct_pipeline {

&#x20; **target** = **AZURE**

&#x20; **pipelineFile** = "azure-pipelines.yml"

&#x20; **enableCI** = **true**

&#x20; **enableCD** = **true**

&#x20; **enableCT** = **true**



&#x20; **stages** = [

&#x20;   **PipelineStage** {

&#x20;     **kind** = **VALIDATE_DATA**

&#x20;     **name** = "data_validation"

&#x20;     **required** = **true**

&#x20;     **tool** = "python"

&#x20;     **outputArtifact** = "reports/data-quality.json"

&#x20;   },

&#x20;   **PipelineStage** {

&#x20;     **kind** = **VERSION_DATA**

&#x20;     **name** = "data_versioning"

&#x20;     **required** = **true**

&#x20;     **tool** = "dvc"

&#x20;     **outputArtifact** = "dvc.lock"

&#x20;   },

&#x20;   **PipelineStage** {

&#x20;     **kind** = **TRAIN**

&#x20;     **name** = "training"

&#x20;     **required** = **true**

&#x20;     **tool** = "pytorch"

&#x20;     **outputArtifact** = "models/resnet.pt"

&#x20;   },

&#x20;   **PipelineStage** {

&#x20;     **kind** = **TRACK_EXPERIMENT**

&#x20;     **name** = "experiment_tracking"

&#x20;     **required** = **true**

&#x20;     **tool** = "mlflow"

&#x20;     **outputArtifact** = "mlruns"

&#x20;   },

&#x20;   **PipelineStage** {

&#x20;     **kind** = **EVALUATE**

&#x20;     **name** = "evaluation"

&#x20;     **required** = **true**

&#x20;     **tool** = "python"

&#x20;     **outputArtifact** = "metrics/eval.json"

&#x20;   },

&#x20;   **PipelineStage** {

&#x20;     **kind** = **REGISTER_MODEL**

&#x20;     **name** = "model_registry"

&#x20;     **required** = **true**

&#x20;     **tool** = "azureml"

&#x20;     **outputArtifact** = "registry/vision_defects_model_v1"

&#x20;   },

&#x20;   **PipelineStage** {

&#x20;     **kind** = **DECISION_GATE**

&#x20;     **name** = "decision_gate"

&#x20;     **required** = **true**

&#x20;     **tool** = "mlopsdsl"

&#x20;     **outputArtifact** = "reports/decision.json"

&#x20;   },

&#x20;   **PipelineStage** {

&#x20;     **kind** = **DEPLOY**

&#x20;     **name** = "deployment"

&#x20;     **required** = **true**

&#x20;     **tool** = "terraform"

&#x20;     **outputArtifact** = "infra/terraform/tfplan"

&#x20;   },

&#x20;   **PipelineStage** {

&#x20;     **kind** = **MONITOR**

&#x20;     **name** = "monitoring"

&#x20;     **required** = **true**

&#x20;     **tool** = "datadog"

&#x20;     **outputArtifact** = "metrics/runtime.json"

&#x20;   },

&#x20;   **PipelineStage** {

&#x20;     **kind** = **RETRAIN**

&#x20;     **name** = "retraining_check"

&#x20;     **required** = **true**

&#x20;     **tool** = "mlopsdsl"

&#x20;     **outputArtifact** = "reports/retraining-decision.json"

&#x20;   }

&#x20; ]

}



**FeatureStoreSpec** azure_blob_offline {

&#x20; **kind** = "feast"

&#x20; **offlineStore** = "azure_blob_offline"

&#x20; **onlineStore** = "redis-online"

}



**OperationalPlan** vision_2025Q1 {

&#x20; **rationale** = "vision_2025Q1"

&#x20; **compute** = **GPU_SINGLE**

&#x20; **packaging** = **CONTAINER**

&#x20; **trainMode** = **SCRIPT**

&#x20; **servingMode** = **API**

&#x20; **entryCmd** = "python serve.py"



&#x20; **resources** = [

&#x20;   **Resource** { **key** = "GPU" **amount** = 1 },

&#x20;   **Resource** { **key** = "CPU" **amount** = 4 },

&#x20;   **Resource** { **key** = "memory" **amount** = 16384 }

&#x20; ]



&#x20; **featureStore** = azure_blob_offline



&#x20; **secrets** = [

&#x20;   **SecretRef** { **name** = "azure-ml-registry-creds" },

&#x20;   **SecretRef** { **name** = "azure-storage-creds" }

&#x20; ]



&#x20; **ciTarget** = **AZURE**

&#x20; **baseImage** = "mcr.microsoft.com/azureml/openmpi4.1.0-ubuntu20.04"

&#x20; **extraDeps** = ["torchvision==0.17.0","opencv-python==4.9.0","mlflow==2.10.0"]



&#x20; **retries** = 2

&#x20; **cache** = **true**

&#x20; **queue** = "gpu-training"

}



**InfraPlan** vision_prod_aks {

&#x20; **provider** = **AZURE**

&#x20; **accountId** = "azure-subscription-1234"

&#x20; **region** = "westeurope"

&#x20; **orchestrator** = **KUBERNETES**

&#x20; **clusterName** = "vision_prod_aks"

&#x20; **namespace** = "vision-serving"



&#x20; **environment** = **EnvironmentSpec** {

&#x20;   **name** = "prod"

&#x20; }



&#x20; **backend** = **TerraformBackendSpec** {

&#x20;   **kind** = "azurerm"

&#x20;   **key** = "vision/prod/terraform.tfstate"

&#x20;   **resourceGroup** = "rg-vision-tfstate"

&#x20;   **storageAccount** = "visiontfstateprod"

&#x20;   **container** = "tfstate"

&#x20; }



&#x20; **runtime** = **RuntimeSpec** {

&#x20;   **nodePools** = [

&#x20;     **NodePoolSpec** {

&#x20;       **name** = "system"

&#x20;       **machineType** = "Standard_D2s_v5"

&#x20;       **minNodes** = 1

&#x20;       **desiredNodes** = 2

&#x20;       **maxNodes** = 4

&#x20;       **gpu** = **false**

&#x20;       **spot** = **false**

&#x20;     },

&#x20;     **NodePoolSpec** {

&#x20;       **name** = "compute"

&#x20;       **machineType** = "Standard_D2s_v5"

&#x20;       **minNodes** = 1

&#x20;       **desiredNodes** = 1

&#x20;       **maxNodes** = 1

&#x20;       **gpu** = **false**

&#x20;       **spot** = **false**

&#x20;     }

&#x20;   ]

&#x20; }



&#x20; **network** = **NetworkSpec** {

&#x20;   **exposure** = **PUBLIC**

&#x20;   **domain** = "vision.azure.company.com"

&#x20;   **tls** = **true**

&#x20;   **ingressClass** = "nginx"

&#x20;   **cidr** = "10.50.0.0/16"

&#x20;   **subnets** = ["10.50.1.0/24","10.50.2.0/24"]

&#x20; }



&#x20; **storage** = [

&#x20;   **StorageTarget** {

&#x20;     **kind** = **OBJECT_STORE**

&#x20;     **purpose** = "datasets"

&#x20;     **name** = "azure://vision-datasets"

&#x20;     **retention** = "180d"

&#x20;     **encrypted** = **true**

&#x20;   },

&#x20;   **StorageTarget** {

&#x20;     **kind** = **OBJECT_STORE**

&#x20;     **purpose** = "models"

&#x20;     **name** = "azure://vision-model-registry"

&#x20;     **retention** = "infinite"

&#x20;     **encrypted** = **true**

&#x20;   },

&#x20;   **StorageTarget** {

&#x20;     **kind** = **OBJECT_STORE**

&#x20;     **purpose** = "metrics"

&#x20;     **name** = "azure://vision-metrics"

&#x20;     **retention** = "365d"

&#x20;     **encrypted** = **true**

&#x20;   }

&#x20; ]



&#x20; **secrets** = **SecretBackendSpec** {

&#x20;   **kind** = **VAULT**

&#x20;   **pathPrefix** = "kv/vision-prod/"

&#x20; }



&#x20; **observability** = **ObservabilitySpec** {

&#x20;   **stack** = **DATADOG**

&#x20;   **logsEnabled** = **true**

&#x20;   **metricsEnabled** = **true**

&#x20;   **tracesEnabled** = **false**

&#x20; }



&#x20; **spotAllowed** = **false**

&#x20; **maxMonthlyBudget** = 3000

&#x20; **piiData** = **false**

&#x20; **gdprRestricted** = **true**

}



**ProjectSpec** {

&#x20; **name** = "validation_c00"

&#x20; **data** = defects_ds_v1

&#x20; **features** = vision_features_v1

&#x20; **train** = pytorch

&#x20; **eval** = accuracy

&#x20; **registry** = azureml

&#x20; **serving** = azure

&#x20; **monitoring** = ^monitoring



&#x20; **tracking** = tracking_mlflow

&#x20; **versioning** = dvc_versioning

&#x20; **modelRegistry** = model_registry

&#x20; **decisionPolicy** = deployment_gate

&#x20; **retrainingPolicy** = drift_retraining

&#x20; **governance** = governance_policy

&#x20; **cicdct** = cicdct_pipeline

}



**MetaModelSpec** {

&#x20; **version** = "0.3.0"

&#x20; **schemaVersion** = "2026.05"

&#x20; **createdBy** = "mlops-team"

&#x20; **createdAt** = "2026-05-11"



&#x20; **project** = validation_c00

&#x20; **plan** = vision_2025Q1

&#x20; **infra** = vision_prod_aks



&#x20; **workflow** = **MlWorkflow** {

&#x20; **tasks** = [

&#x20;   **MlTask** {

&#x20;     **taskId** = w_prepare

&#x20;     **type** = **PREPARE**

&#x20;     **produces** = [

&#x20;       **Artifact** { **path** = "images/prepared" **kind** = **DATASET** }

&#x20;     ]

&#x20;   },

&#x20;   **MlTask** {

&#x20;     **taskId** = w_version

&#x20;     **type** = **VERSION**

&#x20;     **produces** = [

&#x20;       **Artifact** { **path** = "dvc.lock" **kind** = **OTHER** }

&#x20;     ]

&#x20;     **consumes** = [

&#x20;       **Artifact** { **path** = "images/prepared" **kind** = **DATASET** }

&#x20;     ]

&#x20;   },

&#x20;   **MlTask** {

&#x20;     **taskId** = w_train

&#x20;     **type** = **TRAIN**

&#x20;     **produces** = [

&#x20;       **Artifact** { **path** = "models/resnet.pt" **kind** = **MODEL** }

&#x20;     ]

&#x20;     **consumes** = [

&#x20;       **Artifact** { **path** = "images/prepared" **kind** = **DATASET** }

&#x20;     ]

&#x20;   },

&#x20;   **MlTask** {

&#x20;     **taskId** = w_track

&#x20;     **type** = **TRACK**

&#x20;     **produces** = [

&#x20;       **Artifact** { **path** = "mlruns" **kind** = **OTHER** }

&#x20;     ]

&#x20;     **consumes** = [

&#x20;       **Artifact** { **path** = "models/resnet.pt" **kind** = **MODEL** }

&#x20;     ]

&#x20;   },

&#x20;   **MlTask** {

&#x20;     **taskId** = w_eval

&#x20;     **type** = **EVAL**

&#x20;     **produces** = [

&#x20;       **Artifact** { **path** = "metrics/eval.json" **kind** = **METRICS** }

&#x20;     ]

&#x20;     **consumes** = [

&#x20;       **Artifact** { **path** = "models/resnet.pt" **kind** = **MODEL** }

&#x20;     ]

&#x20;   },

&#x20;   **MlTask** {

&#x20;     **taskId** = w_decide

&#x20;     **type** = **DECIDE**

&#x20;     **produces** = [

&#x20;       **Artifact** { **path** = "reports/decision.json" **kind** = **OTHER** }

&#x20;     ]

&#x20;     **consumes** = [

&#x20;       **Artifact** { **path** = "metrics/eval.json" **kind** = **METRICS** }

&#x20;     ]

&#x20;   },

&#x20;   **MlTask** {

&#x20;     **taskId** = w_register

&#x20;     **type** = **REGISTER**

&#x20;     **produces** = [

&#x20;       **Artifact** { **path** = "registry/vision_defects_model_v1" **kind** = **MODEL** }

&#x20;     ]

&#x20;     **consumes** = [

&#x20;       **Artifact** { **path** = "models/resnet.pt" **kind** = **MODEL** },

&#x20;       **Artifact** { **path** = "reports/decision.json" **kind** = **OTHER** }

&#x20;     ]

&#x20;   },

&#x20;   **MlTask** {

&#x20;     **taskId** = w_deploy

&#x20;     **type** = **DEPLOY**

&#x20;     **produces** = [

&#x20;       **Artifact** { **path** = "infra/terraform/tfplan" **kind** = **OTHER** }

&#x20;     ]

&#x20;     **consumes** = [

&#x20;       **Artifact** { **path** = "registry/vision_defects_model_v1" **kind** = **MODEL** }

&#x20;     ]

&#x20;   },

&#x20;   **MlTask** {

&#x20;     **taskId** = w_monitor

&#x20;     **type** = **MONITOR**

&#x20;     **produces** = [

&#x20;       **Artifact** { **path** = "metrics/runtime.json" **kind** = **METRICS** }

&#x20;     ]

&#x20;     **consumes** = [

&#x20;       **Artifact** { **path** = "infra/terraform/tfplan" **kind** = **OTHER** }

&#x20;     ]

&#x20;   },

&#x20;   **MlTask** {

&#x20;     **taskId** = w_retrain

&#x20;     **type** = **RETRAIN**

&#x20;     **produces** = [

&#x20;       **Artifact** { **path** = "reports/retraining-decision.json" **kind** = **OTHER** }

&#x20;     ]

&#x20;     **consumes** = [

&#x20;       **Artifact** { **path** = "metrics/runtime.json" **kind** = **METRICS** }

&#x20;     ]

&#x20;   }

&#x20; ]



&#x20; **edges** = [

&#x20;   **Edge** { **from** = w_prepare **to** = w_version },

&#x20;   **Edge** { **from** = w_version **to** = w_train },

&#x20;   **Edge** { **from** = w_train **to** = w_track },

&#x20;   **Edge** { **from** = w_train **to** = w_eval },

&#x20;   **Edge** { **from** = w_eval **to** = w_decide },

&#x20;   **Edge** { **from** = w_decide **to** = w_register },

&#x20;   **Edge** { **from** = w_register **to** = w_deploy },

&#x20;   **Edge** { **from** = w_deploy **to** = w_monitor },

&#x20;   **Edge** { **from** = w_monitor **to** = w_retrain }

&#x20; ]

}

}


