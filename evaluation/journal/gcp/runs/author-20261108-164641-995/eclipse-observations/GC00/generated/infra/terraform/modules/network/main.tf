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


resource "google_compute_network" "main" {
  name                    = "vpc-${var.project_name}-${var.environment}"
  auto_create_subnetworks = false
}

resource "google_compute_subnetwork" "private" {
  count         = 2
  name          = "snet-${var.project_name}-${var.environment}-${count.index}"
  ip_cidr_range = ["10.72.1.0/24", "10.72.2.0/24"][count.index]
  region        = var.region
  network       = google_compute_network.main.id
}

output "network_id" {
  value = google_compute_network.main.id
}

output "private_subnets" {
  value = google_compute_subnetwork.private[*].id
}

