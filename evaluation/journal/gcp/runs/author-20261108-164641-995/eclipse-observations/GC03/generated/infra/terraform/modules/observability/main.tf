variable "project_name" {
  type = string
  description = "Project name."
}

variable "environment" {
  type = string
  description = "Environment."
}

variable "region" {
  type = string
  description = "Region."
}

variable "account_id" {
  type = string
  description = "Cloud account or project id."
}

variable "cluster_name" {
  type = string
  description = "Cluster name."
}

variable "namespace" {
  type = string
  description = "Namespace."
}

variable "domain" {
  type = string
  description = "Domain."
  default = ""
}

variable "public_exposure" {
  type = bool
  description = "Public exposure."
  default = false
}

variable "tls_enabled" {
  type = bool
  description = "TLS enabled."
  default = false
}

variable "pii_data" {
  type = bool
  description = "PII data."
  default = false
}

variable "gdpr_restricted" {
  type = bool
  description = "GDPR restricted."
  default = false
}

variable "data_source" {
  type = string
  description = "Data source."
  default = ""
}

variable "data_modality" {
  type = string
  description = "Data modality."
  default = ""
}

variable "data_location" {
  type = string
  description = "Data location."
  default = ""
}

variable "tags" {
  type    = map(string)
  default = {}
}


resource "google_logging_project_sink" "mlops" {
  name        = "${var.project_name}-${var.environment}-logs"
  destination = "logging.googleapis.com/projects/${var.account_id}"
  filter      = "resource.type=\"k8s_container\""
}

output "workspace_ref" {
  value = google_logging_project_sink.mlops.id
}

