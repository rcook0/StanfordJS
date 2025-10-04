import numpy as np
import sjs_svm as sjs

def test_linear_kernel_dot_product():
    k = sjs.LinearKernel()
    x = np.array([1.0, 2.0, 3.0])
    z = np.array([0.5, -1.0, 4.0])
    # decision() isn't exposed on kernel; verify via model path with one SV
    m = sjs.SVMModel()
    m.support_vectors = np.array([z])
    m.alpha_y = np.array([1.0])
    m.bias = 0.0
    got = m.decision(x, k)
    expect = float(np.dot(z, x))
    assert abs(got - expect) < 1e-12

def test_rbf_kernel_behaviour_monotonic():
    k = sjs.RBFKernel(0.5)
    # emulate via model decision with one SV at origin
    m = sjs.SVMModel()
    m.support_vectors = np.array([[0.0, 0.0]])
    m.alpha_y = np.array([1.0])
    m.bias = 0.0
    x_close = np.array([0.1, 0.1])
    x_far   = np.array([3.0, 4.0])
    assert m.decision(x_close, k) > m.decision(x_far, k)
