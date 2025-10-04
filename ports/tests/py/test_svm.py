import numpy as np
import sjs_svm as sjs

def test_smoke_linear():
    X = np.array([[1.0,0.0],[0.0,1.0]])
    y = np.array([1,-1], dtype=np.int32)
    t = sjs.SMOTrainer()
    m = t.fit_linear(X, y, sjs.SVMParams())
    k = sjs.LinearKernel()
    assert isinstance(m.bias, float)
    s = m.decision(np.array([1.0,0.0]), k)
    assert isinstance(s, float)
