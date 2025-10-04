cmake_minimum_required(VERSION 3.20)
project(sjs_core LANGUAGES CXX)

set(CMAKE_CXX_STANDARD 17)
set(CMAKE_CXX_STANDARD_REQUIRED ON)

include(FetchContent)
# Eigen (header-only)
FetchContent_Declare(
  eigen
  GIT_REPOSITORY https://gitlab.com/libeigen/eigen.git
  GIT_TAG 3.4.0
)
FetchContent_MakeAvailable(eigen)

find_package(Python3 COMPONENTS Development.Module REQUIRED)
find_package(pybind11 CONFIG REQUIRED)

add_library(sjs_core STATIC
  src/svm/trainer.cpp
)
target_include_directories(sjs_core PUBLIC include)
target_link_libraries(sjs_core PUBLIC Eigen3::Eigen)

# Python extension module
pybind11_add_module(sjs_svm bindings/py_svm.cpp)
target_link_libraries(sjs_svm PRIVATE sjs_core)

option(SJS_PROFILE "Enable lightweight profiling output" OFF)
if (SJS_PROFILE)
  add_compile_definitions(SJS_PROFILE=1)
endif()

add_executable(svm_inner bench/svm_inner.cpp)
target_link_libraries(svm_inner PRIVATE sjs_core)

enable_testing()
add_executable(cpp_tests tests/cpp/test_main.cpp)
target_link_libraries(cpp_tests PRIVATE sjs_core)
add_test(NAME cpp_tests COMMAND cpp_tests)


add_executable(test_kernel_sanity tests/cpp/test_kernel_sanity.cpp)
target_link_libraries(test_kernel_sanity PRIVATE sjs_core)
add_test(NAME test_kernel_sanity COMMAND test_kernel_sanity)
