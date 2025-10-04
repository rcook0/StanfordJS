#pragma once
#include <chrono>
#include <cstdio>
#include <string>

namespace sjs {
#ifdef SJS_PROFILE
struct Profiler {
  std::string name;
  std::chrono::high_resolution_clock::time_point t0;
  explicit Profiler(std::string label) : name(std::move(label)), t0(std::chrono::high_resolution_clock::now()) {}
  ~Profiler() {
    auto t1 = std::chrono::high_resolution_clock::now();
    auto ms = std::chrono::duration_cast<std::chrono::microseconds>(t1 - t0).count() / 1000.0;
    std::fprintf(stderr, "[SJS] %s: %.3f ms\n", name.c_str(), ms);
  }
};
#define SJS_PROFILE_SCOPE(label) sjs::Profiler _sjs_profiler_scope(label)
#else
#define SJS_PROFILE_SCOPE(label) do {} while(0)
#endif
} // namespace sjs
