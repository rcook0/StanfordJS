import numpy as np
from python.sugar import fit_svm, predict_batch

def test_predict_batch_shapes():
    # minimal model by calling fit on stub trainer
    X = np.array([[1.0,0.0],[0.0,1.0]])
    y = np.array([1,-1], dtype=np.int32)
    m = fit_svm(X, y, kernel='linear')
    preds = predict_batch(m, X, kernel='linear')
    assert preds.shape == (2,)
