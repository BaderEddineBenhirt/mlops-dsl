terraform {
  required_version = ">= 1.6.0"
  required_providers {
    google = { source = "hashicorp/google", version = "= 5.45.2" }
  }
}

provider "google" {
  project = var.account_id
  region  = var.region
}
