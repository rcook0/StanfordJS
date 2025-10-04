import os, json, numpy as np, pytest
import sjs_svm as sjs

@pytest.mark.xfail(reason="Enable once SMO trainer is implemented and model I/O is wired")
def test_java_parity_vector_and_margin(tmp_path):
    # Golden vector schema (to be produced from Java):
    # {"kernel":"rbf","gamma":0.5,"bias":b,"support_vectors":[[...],[...]],"alpha_y":[...],
    #  "X_eval":[[...],[...]], "margins":[...]}
    golden_path = os.environ.get("SJS_JAVA_GOLDEN")
    if golden_path is None or not os.path.exists(golden_path):
        pytest.skip("Golden file not provided")
    with open(golden_path) as f:
        g = json.load(f)
    if g["kernel"] == "rbf":
        k = sjs.RBFKernel(float(g["gamma"]))
    else:
        k = sjs.LinearKernel()
    m = sjs.SVMModel()
    m.support_vectors = np.asarray(g["support_vectors"], dtype=float)
    m.alpha_y = np.asarray(g["alpha_y"], dtype=float)
    m.bias = float(g["bias"])
    X_eval = np.asarray(g["X_eval"], dtype=float)
    got = [m.decision(x, k) for x in X_eval]
    assert np.allclose(got, np.asarray(g["margins"], dtype=float), atol=1e-8)
