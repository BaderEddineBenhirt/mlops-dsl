terraform {
  required_version = ">= 1.6.0, < 2.0.0"
  required_providers {
    google = { source = "hashicorp/google", version = "= 5.45.2" }
  }
}
provider "google" {
  project = var.account_id
  region  = var.region
}
variable "account_id" {
  type    = string
  default = "mlops-gcp-study"
}
variable "region" {
  type    = string
  default = "europe-west4"
}
variable "cluster_name" {
  type    = string
  default = "fraud-gcp-study"
}
variable "public_exposure" {
  type    = bool
  default = true
}
variable "tls_enabled" {
  type    = bool
  default = false
}
resource "google_compute_network" "study" {
  name                    = "fraud-study-vpc"
  auto_create_subnetworks = false
}
resource "google_compute_subnetwork" "study" {
  count         = 2
  name          = "fraud-study-subnet-${count.index}"
  ip_cidr_range = ["10.72.1.0/24", "10.72.2.0/24"][count.index]
  region        = var.region
  network       = google_compute_network.study.id
}
resource "google_container_cluster" "study" {
  name                     = var.cluster_name
  location                 = var.region
  network                  = google_compute_network.study.id
  subnetwork               = google_compute_subnetwork.study[0].id
  remove_default_node_pool = true
  initial_node_count       = 1
  ip_allocation_policy {}
}
resource "google_container_node_pool" "cpu" {
  name               = "cpu"
  location           = var.region
  cluster            = google_container_cluster.study.name
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
resource "google_storage_bucket" "dataset" {
  name                        = "fraud-gcp-dev-dataset"
  location                    = var.region
  uniform_bucket_level_access = true
}
resource "google_storage_bucket" "artifacts" {
  name                        = "fraud-gcp-dev-artifacts"
  location                    = var.region
  uniform_bucket_level_access = true
}
resource "google_secret_manager_secret" "study" {
  secret_id = "fraud-gcp-dev-mlops"
  replication {
    auto {}
  }
}
resource "google_logging_project_sink" "study" {
  name        = "fraud-gcp-dev-logs"
  destination = "logging.googleapis.com/projects/${var.account_id}"
  filter      = "resource.type=\"k8s_container\""
}
