

def save_model(model, path, kernel='rbf', gamma=None):
    import sjs_svm as sjs
    cfg = sjs.KernelCfg(); cfg.name = kernel
    if kernel == 'rbf':
        cfg.gamma = float(gamma if gamma is not None else 0.0)
    sjs.save_model(model, cfg, path)

def load_model(path):
    import sjs_svm as sjs
    m, cfg = sjs.load_model(path)
    k = sjs.LinearKernel() if cfg.name == 'linear' else sjs.RBFKernel(cfg.gamma)
    return m, k
