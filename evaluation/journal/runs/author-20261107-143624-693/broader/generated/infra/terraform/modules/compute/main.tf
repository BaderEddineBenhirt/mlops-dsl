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


variable "network_id" {
  type = string
}

variable "private_subnets" {
  type    = list(string)
  default = []
}

resource "azurerm_resource_group" "compute" {
  name     = "rg-${var.project_name}-${var.environment}-compute"
  location = var.region
  tags     = var.tags
}

resource "azurerm_kubernetes_cluster" "main" {
  name                = var.cluster_name
  location            = azurerm_resource_group.compute.location
  resource_group_name = azurerm_resource_group.compute.name
  dns_prefix          = substr(replace(lower(var.cluster_name), "_", "-"), 0, 45)

  default_node_pool {
    name                = "system"
    vm_size             = "Standard_D2s_v5"
    vnet_subnet_id      = length(var.private_subnets) > 0 ? var.private_subnets[0] : null
    enable_auto_scaling = true
    node_count          = 2
    min_count           = 1
    max_count           = 4
  }

  identity {
    type = "SystemAssigned"
  }

  workload_identity_enabled = true
  oidc_issuer_enabled       = true
  tags                      = var.tags
}

resource "azurerm_kubernetes_cluster_node_pool" "compute" {
  name                  = "compute"
  kubernetes_cluster_id = azurerm_kubernetes_cluster.main.id
  vm_size               = "Standard_D2s_v5"
  enable_auto_scaling   = true
  node_count            = 1
  min_count             = 1
  max_count             = 1
  vnet_subnet_id        = length(var.private_subnets) > 0 ? var.private_subnets[0] : null
  tags                  = var.tags
}

output "cluster_name" {
  value = azurerm_kubernetes_cluster.main.name
}

