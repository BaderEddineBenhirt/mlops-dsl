"""Statistics for fixed benchmarks and a matched-block crossover experiment.

The independent randomisation/resampling unit in Study B is a two-person
allocation block. Task observations never inflate the effective sample size.
"""
import itertools
import math

import numpy as np


def bootstrap_mean(values, seed=1847, repetitions=10000):
    x = np.asarray(values, dtype=float)
    if len(x) < 2 or not np.isfinite(x).all():
        raise ValueError("At least two finite independent block values required")
    rng = np.random.default_rng(seed)
    means = np.concatenate([
        rng.choice(x, size=(min(500, repetitions - i), len(x)), replace=True).mean(axis=1)
        for i in range(0, repetitions, 500)])
    return [float(v) for v in np.quantile(means, [.025, .975])]


def randomisation_test(block_differences, seed=1847, repetitions=19999):
    """Two-sided assignment test, swapping tool order within matched blocks."""
    x = np.asarray(block_differences, dtype=float)
    if len(x) < 2 or not np.isfinite(x).all():
        raise ValueError("At least two finite independent allocation blocks required")
    observed = abs(float(x.mean()))
    if len(x) <= 18:
        extreme = sum(abs(float(np.dot(signs, x) / len(x))) >= observed - 1e-12
                      for signs in itertools.product((-1, 1), repeat=len(x)))
        return {"p_two_sided": extreme / (2 ** len(x)), "method": "exact_block_randomisation",
                "assignments": 2 ** len(x)}
    rng = np.random.default_rng(seed)
    extreme = 0
    for i in range(0, repetitions, 500):
        n = min(500, repetitions - i)
        signs = rng.choice([-1., 1.], size=(n, len(x)))
        extreme += int(np.sum(np.abs(signs @ x / len(x)) >= observed - 1e-12))
    return {"p_two_sided": (extreme + 1) / (repetitions + 1),
            "method": "monte_carlo_block_randomisation_plus_one", "assignments": repetitions}


def paired_summary(block_differences, seed=1847):
    x = np.asarray(block_differences, dtype=float)
    if len(x) < 2:
        return {"status": "INSUFFICIENT_BLOCKS", "blocks": len(x)}
    return {"status": "ANALYSED", "blocks": len(x), "mean_difference": float(x.mean()),
            "median_difference": float(np.median(x)), "range": [float(x.min()), float(x.max())],
            "ci95_block_percentile_bootstrap": bootstrap_mean(x, seed),
            **randomisation_test(x, seed)}


def plan_power(delta, block_sd, target=.8, alpha=.05, seed=1847, simulations=1000):
    """Sensitivity calculation under an explicit Gaussian BLOCK-contrast model.

    Uses the same sign-randomisation rejection rule as the primary analysis.
    Inputs are assumptions, never observed effects. A pilot can inform block_sd.
    """
    if not (0 < delta <= 1 and 0 < block_sd <= 1 and 0 < alpha < 1 and .5 < target < 1):
        raise ValueError("Specify positive normalised-score delta/SD and valid alpha/power")
    rng = np.random.default_rng(seed)
    result = []
    for blocks in (6, 8, 10, 12, 16, 20, 24, 32, 40, 48, 64):
        # Exact enumeration for small designs, otherwise 1999 sign assignments.
        if blocks <= 10:
            signs = np.asarray(list(itertools.product((-1., 1.), repeat=blocks)))
            exact = True
        else:
            signs = rng.choice([-1., 1.], size=(1999, blocks))
            exact = False
        hits = 0
        for start in range(0, simulations, 50):
            n = min(50, simulations - start)
            x = rng.normal(delta, block_sd, size=(n, blocks))
            observed = np.abs(x.mean(axis=1))
            extreme = np.sum(np.abs(x @ signs.T / blocks) >= observed[:, None] - 1e-12, axis=1)
            p = extreme / len(signs) if exact else (extreme + 1) / (len(signs) + 1)
            hits += int(np.sum(p <= alpha))
        power = hits / simulations
        se = math.sqrt(power * (1 - power) / simulations)
        result.append({"blocks": blocks, "participants": 2 * blocks,
                       "power_estimate": power, "monte_carlo_se": se})
    candidates = [r for r in result if r["power_estimate"] - 1.96 * r["monte_carlo_se"] >= target]
    return {"status": "ASSUMPTION_BASED_PLANNING_ONLY", "delta": delta,
            "block_sd": block_sd, "distribution": "Gaussian independent allocation-block contrasts",
            "alpha_two_sided": alpha, "target_power": target, "simulations": simulations,
            "seed": seed, "grid": result,
            "candidate_participants": candidates[0]["participants"] if candidates else None,
            "warning": "Review assumptions and feasibility before freezing recruitment. Not observed power."}
