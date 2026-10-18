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


resource "azurerm_resource_group" "obs" {
  name     = "rg-${var.project_name}-${var.environment}-obs"
  location = var.region
  tags     = var.tags
}

resource "azurerm_log_analytics_workspace" "mlops" {
  name                = "law-${var.project_name}-${var.environment}"
  location            = azurerm_resource_group.obs.location
  resource_group_name = azurerm_resource_group.obs.name
  sku                 = "PerGB2018"
  retention_in_days   = var.gdpr_restricted ? 30 : 90
  tags                = var.tags
}

output "workspace_ref" {
  value = azurerm_log_analytics_workspace.mlops.id
}

