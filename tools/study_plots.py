"""Scientific plots. Inputs must be measured observations, never demo outcomes."""
from collections import defaultdict


def participant_plot(records, target):
    import matplotlib
    matplotlib.use("Agg")
    import matplotlib.pyplot as plt
    groups = defaultdict(lambda: defaultdict(list))
    for row in records:
        groups[row["participant_id"]][row["approach"]].append(row["normalised_capped_time"])
    differences = sorted((sum(a["baseline"]) / 4 - sum(a["dsl"]) / 4, pid) for pid, a in groups.items())
    fig, ax = plt.subplots(figsize=(7.2, max(3., len(groups) * .19)))
    ax.axvline(0, color="0.6", lw=1)
    ax.scatter([x for x, _ in differences], range(len(groups)), color="#185f82", s=24)
    ax.set_yticks(range(len(groups)), [p for _, p in differences])
    ax.set_xlabel("Baseline minus DSL: mean normalised capped time")
    ax.set_title("Observed participant contrasts")
    ax.grid(axis="x", alpha=.2)
    fig.tight_layout(); fig.savefig(target, dpi=200); plt.close(fig)
