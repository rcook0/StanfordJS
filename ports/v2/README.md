# sjs-core (C++ + Python)


## CI
- GitHub Actions runs C++ tests and Python wheel builds per PR.
- Wheels are also built via `cibuildwheel` for manylinux.

## CMake Presets
- Use `cmake --preset release-avx2` for AVX2-optimized builds on x86_64.

## Repo Bootstrap
```bash
GIT_REMOTE_URL=https://github.com/your/repo.git ./scripts/bootstrap_repo.sh
```
