from pathlib import Path
import yaml

def load_yaml(path):
    path = Path(path)
    if not path.exists():
        raise FileNotFoundError(f'YAML policy file not found: {path}')
    with path.open('r', encoding='utf-8') as file:
        content = yaml.safe_load(file)
    if content is None:
        raise ValueError(f'YAML policy file is empty: {path}')
    return content

def load_decision_policy(path):
    policy = load_yaml(path)
    if 'decisionGate' not in policy:
        raise ValueError("Invalid decision policy: missing 'decisionGate' section.")
    return policy

def load_retraining_policy(path):
    policy = load_yaml(path)
    if 'retraining' not in policy:
        raise ValueError("Invalid retraining policy: missing 'retraining' section.")
    return policy

def load_monitoring_policy(path):
    policy = load_yaml(path)
    if 'monitoring' not in policy:
        raise ValueError("Invalid monitoring policy: missing 'monitoring' section.")
    return policy
