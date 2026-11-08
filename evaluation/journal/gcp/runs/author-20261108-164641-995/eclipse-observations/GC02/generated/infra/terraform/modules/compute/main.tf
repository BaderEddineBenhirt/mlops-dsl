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


variable "network_id" {
  type = string
}

variable "private_subnets" {
  type    = list(string)
  default = []
}

resource "google_container_cluster" "main" {
  name                     = var.cluster_name
  location                 = var.region
  network                  = var.network_id
  subnetwork               = length(var.private_subnets) > 0 ? var.private_subnets[0] : null
  remove_default_node_pool = true
  initial_node_count       = 1
  ip_allocation_policy {}
}

resource "google_container_node_pool" "cpu" {
  name               = "cpu"
  location           = var.region
  cluster            = google_container_cluster.main.name
  initial_node_count = 3
  autoscaling {
    min_node_count = 2
    max_node_count = 5
  }
  node_config {
    machine_type = "e2-standard-2"
    spot         = false
    oauth_scopes = ["https://www.googleapis.com/auth/cloud-platform"]
  }
}

output "cluster_name" {
  value = google_container_cluster.main.name
}

