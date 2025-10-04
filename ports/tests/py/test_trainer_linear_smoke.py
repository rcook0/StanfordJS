import numpy as np, sjs_svm as sjs

def test_linear_trains_and_predicts():
    X = np.array([[2,1],[3,1],[1,2],[1,3]], dtype=float)
    y = np.array([+1,+1,-1,-1], dtype=np.int32)
    t = sjs.SMOTrainer()
    p = sjs.SVMParams(); p.C = 1.0; p.tol = 1e-3; p.max_passes = 5
    m = t.fit_linear(X, y, p)
    k = sjs.LinearKernel()
    preds = [np.sign(m.decision(x, k)) for x in X]
    assert (preds[0] > 0 and preds[1] > 0 and preds[2] < 0 and preds[3] < 0)
