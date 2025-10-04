#pragma once
#include <Eigen/Dense>
#include <vector>

namespace sjs {

struct SVMModel {
  Eigen::MatrixXd support_vectors;  // [n_sv, n_feat]
  Eigen::VectorXd alpha_y;          // [n_sv]
  double bias = 0.0;

  template <typename K>
  double decision(const Eigen::Ref<const Eigen::VectorXd>& x, const K& k) const {
    double s = 0.0;
    for (int i = 0; i < support_vectors.rows(); ++i) {
      s += alpha_y(i) * k(support_vectors.row(i).transpose(), x);
    }
    return s + bias;
  }
};

} // namespace sjs
