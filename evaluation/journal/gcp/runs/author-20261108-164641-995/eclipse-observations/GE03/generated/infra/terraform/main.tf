locals {
  common_tags = merge(var.tags, {
    Project = var.project_name
    Environment = var.environment
    ManagedBy = "mlopsdsl"
    PiiData = tostring(var.pii_data)
    GdprRestricted = tostring(var.gdpr_restricted)
  })
}

module "network" {
  source = "./modules/network"
  project_name = var.project_name
  environment = var.environment
  region = var.region
  account_id = var.account_id
  cluster_name = var.cluster_name
  namespace = var.namespace
  domain = var.domain
  public_exposure = var.public_exposure
  tls_enabled = var.tls_enabled
  pii_data = var.pii_data
  gdpr_restricted = var.gdpr_restricted
  data_source = var.data_source
  data_modality = var.data_modality
  data_location = var.data_location
  tags = local.common_tags
}

module "storage" {
  source = "./modules/storage"
  project_name = var.project_name
  environment = var.environment
  region = var.region
  account_id = var.account_id
  cluster_name = var.cluster_name
  namespace = var.namespace
  domain = var.domain
  public_exposure = var.public_exposure
  tls_enabled = var.tls_enabled
  pii_data = var.pii_data
  gdpr_restricted = var.gdpr_restricted
  data_source = var.data_source
  data_modality = var.data_modality
  data_location = var.data_location
  tags = local.common_tags
}

module "secrets" {
  source = "./modules/secrets"
  project_name = var.project_name
  environment = var.environment
  region = var.region
  account_id = var.account_id
  cluster_name = var.cluster_name
  namespace = var.namespace
  domain = var.domain
  public_exposure = var.public_exposure
  tls_enabled = var.tls_enabled
  pii_data = var.pii_data
  gdpr_restricted = var.gdpr_restricted
  data_source = var.data_source
  data_modality = var.data_modality
  data_location = var.data_location
  tags = local.common_tags
}

module "compute" {
  source = "./modules/compute"
  project_name = var.project_name
  environment = var.environment
  region = var.region
  account_id = var.account_id
  cluster_name = var.cluster_name
  namespace = var.namespace
  domain = var.domain
  public_exposure = var.public_exposure
  tls_enabled = var.tls_enabled
  pii_data = var.pii_data
  gdpr_restricted = var.gdpr_restricted
  data_source = var.data_source
  data_modality = var.data_modality
  data_location = var.data_location
  tags = local.common_tags
  network_id = module.network.network_id
  private_subnets = module.network.private_subnets
}

module "observability" {
  source = "./modules/observability"
  project_name = var.project_name
  environment = var.environment
  region = var.region
  account_id = var.account_id
  cluster_name = var.cluster_name
  namespace = var.namespace
  domain = var.domain
  public_exposure = var.public_exposure
  tls_enabled = var.tls_enabled
  pii_data = var.pii_data
  gdpr_restricted = var.gdpr_restricted
  data_source = var.data_source
  data_modality = var.data_modality
  data_location = var.data_location
  tags = local.common_tags
}
