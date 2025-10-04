import numpy as np
import sjs_svm as sjs

def fit_svm(X, y, kernel='rbf', C=1.0, tol=1e-3, max_passes=10, gamma=None):
    p = sjs.SVMParams()
    p.C, p.tol, p.max_passes = float(C), float(tol), int(max_passes)
    t = sjs.SMOTrainer()
    if kernel == 'linear':
        return t.fit_linear(np.asarray(X, dtype=float), np.asarray(y, dtype=np.int32), p)
    elif kernel == 'rbf':
        if gamma is None:
            gamma = 1.0 / np.asarray(X, dtype=float).shape[1]
        return t.fit_rbf(np.asarray(X, dtype=float), np.asarray(y, dtype=np.int32), float(gamma), p)
    else:
        raise ValueError("kernel must be 'linear' or 'rbf'")

def predict_batch(model: 'sjs.SVMModel', X, kernel='rbf', gamma=None):
    X = np.asarray(X, dtype=float)
    out = np.empty((X.shape[0],), dtype=float)
    if kernel == 'linear':
        k = sjs.LinearKernel()
    elif kernel == 'rbf':
        if gamma is None:
            gamma = 1.0 / X.shape[1]
        k = sjs.RBFKernel(float(gamma))
    else:
        raise ValueError("kernel must be 'linear' or 'rbf'")
    for i in range(X.shape[0]):
        out[i] = model.decision(X[i], k)
    return out
