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
  default = "europe-west4"
}

variable "account_id" {
  type = string
  description = "Cloud account, subscription, or project id."
  default = "mlops-gcp-study"
}

variable "cluster_name" {
  type = string
  description = "Cluster name."
  default = "fraud-gcp-study"
}

variable "namespace" {
  type = string
  description = "Kubernetes namespace."
  default = "fraud-serving"
}

variable "domain" {
  type = string
  description = "DNS domain."
  default = "fraud.example.org"
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
  default = false
}

variable "max_monthly_budget" {
  type = number
  description = "Budget guardrail."
  default = 500
}

variable "data_source" {
  type = string
  description = "Dataset source."
  default = "PARQUET"
}

variable "data_modality" {
  type = string
  description = "Dataset modality."
  default = "TABULAR"
}

variable "data_location" {
  type = string
  description = "Dataset location."
  default = "transactions/v1"
}

variable "tags" {
  type    = map(string)
  default = {}
}
