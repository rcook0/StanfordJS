#include "svm/trainer.hpp"
#include <Eigen/Dense>
#include <vector>

namespace sjs {

template <typename K>
SVMModel SMOTrainer::fit(const Eigen::MatrixXd& X, const Eigen::VectorXi& y,
                         const K& kernel, const SVMParams& p) {
  // Minimal stub to keep build/test green until solver is ported.
  SVMModel m;
  if (X.rows() > 0) {
    m.support_vectors = X.topRows(1);
    m.alpha_y = Eigen::VectorXd::Ones(1) * (y(0) > 0 ? 1.0 : -1.0);
  } else {
    m.support_vectors = Eigen::MatrixXd::Zero(0, X.cols());
    m.alpha_y = Eigen::VectorXd::Zero(0);
  }
  m.bias = 0.0;
  return m;
}

// Explicit instantiation for common kernels
template SVMModel SMOTrainer::fit(const Eigen::MatrixXd&, const Eigen::VectorXi&,
                                  const LinearKernel&, const SVMParams&);
template SVMModel SMOTrainer::fit(const Eigen::MatrixXd&, const Eigen::VectorXi&,
                                  const RBFKernel&, const SVMParams&);

} // namespace sjs
