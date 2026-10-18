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


resource "azurerm_resource_group" "storage" {
  name     = "rg-${var.project_name}-${var.environment}-storage"
  location = var.region
  tags     = var.tags
}

resource "azurerm_storage_account" "main" {
  name                     = substr(replace(lower("${var.project_name}${var.environment}ml"), "-", ""), 0, 24)
  resource_group_name      = azurerm_resource_group.storage.name
  location                 = azurerm_resource_group.storage.location
  account_tier             = "Standard"
  account_replication_type = "LRS"
  min_tls_version          = "TLS1_2"
  tags                     = var.tags
}

resource "azurerm_storage_container" "dataset" {
  name                  = "dataset"
  storage_account_name  = azurerm_storage_account.main.name
  container_access_type = "private"
}

resource "azurerm_storage_container" "artifacts" {
  name                  = "model-artifacts"
  storage_account_name  = azurerm_storage_account.main.name
  container_access_type = "private"
}

output "dataset_uri" {
  value = "az://${azurerm_storage_container.dataset.name}/${var.data_location}"
}

output "artifacts_uri" {
  value = "az://${azurerm_storage_container.artifacts.name}"
}

