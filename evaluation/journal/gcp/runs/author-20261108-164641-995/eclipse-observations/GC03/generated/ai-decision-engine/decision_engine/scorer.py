def normalize_inverse(value, max_value):
    if max_value <= 0:
        return 0.0
    return max(0.0, min(1.0, 1.0 - (value / max_value)))

def get_float(value, default=0.0):
    if value is None:
        return default
    return float(value)

def compute_candidate_score(candidate, drift_score, decision_policy):
    gate = decision_policy['decisionGate']
    min_accuracy = get_float(gate.get('minAccuracy'), 0.0)
    min_f1 = get_float(gate.get('minF1'), 0.0)
    max_latency_ms = get_float(gate.get('maxLatencyMs'), 999999.0)
    max_cost = get_float(gate.get('maxCost'), 999999.0)
    max_drift = get_float(gate.get('maxDrift'), 1.0)
    min_traceability = get_float(gate.get('minTraceabilityScore'), 0.0)

    accuracy = get_float(candidate.get('accuracy'), 0.0)
    f1 = get_float(candidate.get('f1'), 0.0)
    latency_ms = get_float(candidate.get('latency_ms'), 999999.0)
    cost = get_float(candidate.get('cost'), 0.0)
    traceability = get_float(candidate.get('traceability_score'), 0.0)

    failures = []
    if accuracy < min_accuracy:
        failures.append('accuracy_below_threshold')
    if f1 < min_f1:
        failures.append('f1_below_threshold')
    if latency_ms > max_latency_ms:
        failures.append('latency_above_threshold')
    if cost > max_cost:
        failures.append('cost_above_threshold')
    if drift_score > max_drift:
        failures.append('drift_above_threshold')
    if traceability < min_traceability:
        failures.append('traceability_below_threshold')

    latency_score = normalize_inverse(latency_ms, max_latency_ms)
    cost_score = normalize_inverse(cost, max_cost)
    drift_stability = normalize_inverse(drift_score, max_drift)

    final_score = (
        0.40 * f1
        + 0.15 * accuracy
        + 0.15 * latency_score
        + 0.10 * cost_score
        + 0.10 * drift_stability
        + 0.10 * traceability
    )
    return round(final_score, 4), failures
