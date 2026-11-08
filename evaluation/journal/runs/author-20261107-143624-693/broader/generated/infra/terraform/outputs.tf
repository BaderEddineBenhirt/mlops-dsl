output "network_id" {
  value = module.network.network_id
}

output "private_subnets" {
  value = module.network.private_subnets
}

output "cluster_name" {
  value = module.compute.cluster_name
}

output "storage_dataset" {
  value = module.storage.dataset_uri
}

output "storage_artifacts" {
  value = module.storage.artifacts_uri
}

output "secrets_backend" {
  value = module.secrets.backend_ref
}

output "observability_workspace" {
  value = module.observability.workspace_ref
}

