#!/usr/bin/env bash
set -euo pipefail
python -m pip install -U pip build
python -m pip install -U scikit-build-core pybind11 cmake ninja packaging numpy
python -m pip install pytest
python -m build
python -m pip install dist/*.whl
pytest -q
echo "[dev] Build + test complete."
