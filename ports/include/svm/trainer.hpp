#pragma once
#include "kernel.hpp"
#include "model.hpp"

namespace sjs {

struct SVMParams {
  double C = 1.0;
  double tol = 1e-3;
  int max_passes = 10;
};

class SMOTrainer {
 public:
  template <typename K>
  SVMModel fit(const Eigen::MatrixXd& X, const Eigen::VectorXi& y,
               const K& kernel, const SVMParams& p);
};

} // namespace sjs
