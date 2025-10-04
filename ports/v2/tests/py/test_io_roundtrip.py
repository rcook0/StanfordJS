import numpy as np
from python.sugar import save_model, load_model
import sjs_svm as sjs

def test_model_io_roundtrip(tmp_path):
    m = sjs.SVMModel()
    m.support_vectors = np.array([[1.0,0.0],[0.0,1.0]])
    m.alpha_y = np.array([0.5, -0.25])
    m.bias = 0.1
    path = tmp_path/'m.json'
    save_model(m, str(path), kernel='rbf', gamma=0.5)
    m2, k = load_model(str(path))
    x = np.array([0.5,0.5])
    s1 = m.decision(x, sjs.RBFKernel(0.5))
    s2 = m2.decision(x, k)
    assert abs(s1 - s2) < 1e-12
