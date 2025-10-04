# sjs-port skeleton


## Dev quickstart
```bash
./scripts/dev.sh
```

## Microbenchmark
Build with profiling:
```bash
cmake -B build -S . -DCMAKE_BUILD_TYPE=Release -DSJS_PROFILE=ON
cmake --build build -j
./build/svm_inner
```
