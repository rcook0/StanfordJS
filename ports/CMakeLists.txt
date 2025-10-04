cmake_minimum_required(VERSION 3.20)
project(sjs_core)

option(SJS_PROFILE "Enable lightweight profiling output" OFF)
if (SJS_PROFILE)
  add_compile_definitions(SJS_PROFILE=1)
endif()

add_executable(svm_inner bench/svm_inner.cpp)
target_link_libraries(svm_inner PRIVATE sjs_core)
