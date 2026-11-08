def run_retraining_advisor(retraining_policy, drift_report, decision_result):
    policy = retraining_policy['retraining']
    drift_score = float(drift_report.get('drift_score', 0.0))
    drift_threshold = float(policy.get('driftThreshold', 1.0))
    auto_trigger = bool(policy.get('autoTrigger', False))
    strategy = policy.get('strategy', 'UNKNOWN')
    queue = policy.get('retrainingQueue', '')
    global_decision = decision_result['decision_gate_result']['global_decision']
    retraining_required = drift_score >= drift_threshold or global_decision == 'retrain'
    if retraining_required and auto_trigger:
        action = 'trigger_retraining'
    elif retraining_required:
        action = 'recommend_retraining'
    else:
        action = 'no_retraining_required'
    return {
        'retraining_recommendation': {
            'required': retraining_required,
            'action': action,
            'strategy': strategy,
            'retraining_queue': queue,
            'reason': {
                'drift_score': drift_score,
                'drift_threshold': drift_threshold,
                'decision_gate': global_decision
            }
        }
    }
