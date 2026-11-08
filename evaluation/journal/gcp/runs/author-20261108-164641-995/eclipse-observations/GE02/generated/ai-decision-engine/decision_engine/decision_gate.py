from decision_engine.scorer import compute_candidate_score

def decide_candidate(candidate, score, failures, drift_detected):
    if drift_detected:
        decision = 'retrain'
        reasons = ['drift_detected']
    elif failures:
        decision = 'reject'
        reasons = failures
    elif score >= 0.80:
        decision = 'promote'
        reasons = ['all_criteria_satisfied']
    else:
        decision = 'keep_current'
        reasons = ['score_below_promotion_threshold']

    return {
        'model_id': candidate.get('model_id', 'unknown'),
        'score': score,
        'decision': decision,
        'reasons': reasons,
        'metrics': candidate
    }

def run_decision_gate(decision_policy, model_metrics, drift_report):
    drift_score = float(drift_report.get('drift_score', 0.0))
    drift_detected = bool(drift_report.get('drift_detected', False))
    candidate_results = []
    for candidate in model_metrics['candidates']:
        score, failures = compute_candidate_score(candidate, drift_score, decision_policy)
        result = decide_candidate(candidate, score, failures, drift_detected)
        candidate_results.append(result)
    ranked = sorted(candidate_results, key=lambda item: item['score'], reverse=True)
    selected = ranked[0] if ranked else None
    return {
        'decision_gate_result': {
            'selected_model': selected['model_id'] if selected else None,
            'global_decision': selected['decision'] if selected else 'no_candidate',
            'candidates': ranked,
            'drift_context': drift_report
        }
    }
