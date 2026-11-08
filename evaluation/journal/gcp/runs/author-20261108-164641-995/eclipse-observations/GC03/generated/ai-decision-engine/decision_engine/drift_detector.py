import numpy as np
from scipy.stats import wasserstein_distance

def average_wasserstein_score(reference, current, normalizer=10.0):
    scores = []
    for column_index in range(reference.shape[1]):
        score = wasserstein_distance(reference[:, column_index], current[:, column_index])
        scores.append(score)
    return round(min(float(np.mean(scores) / normalizer), 1.0), 4)
