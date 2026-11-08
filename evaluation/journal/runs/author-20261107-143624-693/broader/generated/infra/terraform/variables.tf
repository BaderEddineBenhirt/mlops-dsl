variable "project_name" {
  type = string
  description = "Project name."
}

variable "environment" {
  type = string
  description = "Deployment environment."
  default = "dev"
}

variable "region" {
  type = string
  description = "Cloud region."
  default = "westeurope"
}

variable "account_id" {
  type = string
  description = "Cloud account, subscription, or project id."
  default = "azure-subscription-1234"
}

variable "cluster_name" {
  type = string
  description = "Cluster name."
  default = "vision_prod_aks"
}

variable "namespace" {
  type = string
  description = "Kubernetes namespace."
  default = "vision-serving"
}

variable "domain" {
  type = string
  description = "DNS domain."
  default = "vision.azure.company.com"
}

variable "public_exposure" {
  type = bool
  description = "Whether service is public."
  default = true
}

variable "tls_enabled" {
  type = bool
  description = "Whether TLS is enabled."
  default = true
}

variable "pii_data" {
  type = bool
  description = "Whether workload processes PII."
  default = false
}

variable "gdpr_restricted" {
  type = bool
  description = "Whether GDPR restrictions apply."
  default = true
}

variable "max_monthly_budget" {
  type = number
  description = "Budget guardrail."
  default = 3000
}

variable "data_source" {
  type = string
  description = "Dataset source."
  default = "IMAGE_FOLDER"
}

variable "data_modality" {
  type = string
  description = "Dataset modality."
  default = "IMAGE"
}

variable "data_location" {
  type = string
  description = "Dataset location."
  default = "defects_ds_v1"
}

variable "tags" {
  type    = map(string)
  default = {}
}
