terraform {
  required_version = "= 1.13.0"
  required_providers {
    azurerm = { source = "hashicorp/azurerm", version = "= 3.117.1" }
  }
}
provider "azurerm" {
  features {}
}
variable "region" {
  type    = string
  default = ""
}
variable "public_exposure" {
  type    = bool
  default = true
}
locals {
  prefix = "validation-c00-prod"
  tags   = { Project = "validation_c00", Environment = "prod", ManagedBy = "direct-authoring" }
}
resource "azurerm_resource_group" "main" {
  name     = "rg-${local.prefix}"
  location = var.region
  tags     = local.tags
}
resource "azurerm_virtual_network" "main" {
  name                = "vnet-${local.prefix}"
  location            = var.region
  resource_group_name = azurerm_resource_group.main.name
  address_space       = ["10.50.0.0/16"]
}
resource "azurerm_subnet" "private" {
  count                = 2
  name                 = "snet-${local.prefix}-${count.index}"
  resource_group_name  = azurerm_resource_group.main.name
  virtual_network_name = azurerm_virtual_network.main.name
  address_prefixes     = [["10.50.1.0/24", "10.50.2.0/24"][count.index]]
}
resource "azurerm_kubernetes_cluster" "main" {
  name                = "vision_prod_aks"
  location            = var.region
  resource_group_name = azurerm_resource_group.main.name
  dns_prefix          = "vision-prod-aks"
  default_node_pool {
    name                = "system"
    vm_size             = "Standard_D2s_v5"
    node_count          = 2
    min_count           = 1
    max_count           = 4
    enable_auto_scaling = true
    vnet_subnet_id      = azurerm_subnet.private[0].id
  }
  identity {
    type = "SystemAssigned"
  }
  workload_identity_enabled = true
  oidc_issuer_enabled       = true
}
resource "azurerm_kubernetes_cluster_node_pool" "compute" {
  name                  = "compute"
  kubernetes_cluster_id = azurerm_kubernetes_cluster.main.id
  vm_size               = "Standard_D2s_v5"
  node_count            = 1
  min_count             = 1
  max_count             = 1
  enable_auto_scaling   = true
  vnet_subnet_id        = azurerm_subnet.private[0].id
}
resource "azurerm_storage_account" "main" {
  name                     = "validationc00prodml"
  resource_group_name      = azurerm_resource_group.main.name
  location                 = var.region
  account_tier             = "Standard"
  account_replication_type = "LRS"
  min_tls_version          = "TLS1_2"
}
resource "azurerm_storage_container" "artifacts" {
  for_each              = toset(["dataset", "model-artifacts"])
  name                  = each.value
  storage_account_name  = azurerm_storage_account.main.name
  container_access_type = "private"
}
data "azurerm_client_config" "current" {}
resource "azurerm_key_vault" "main" {
  name                       = "kvvalidationc00prod"
  location                   = var.region
  resource_group_name        = azurerm_resource_group.main.name
  tenant_id                  = data.azurerm_client_config.current.tenant_id
  sku_name                   = "standard"
  soft_delete_retention_days = 7
  purge_protection_enabled   = false
}
resource "azurerm_log_analytics_workspace" "main" {
  name                = "law-${local.prefix}"
  location            = var.region
  resource_group_name = azurerm_resource_group.main.name
  sku                 = "PerGB2018"
  retention_in_days   = 30
}
output "cluster_name" {
  value = azurerm_kubernetes_cluster.main.name
}
