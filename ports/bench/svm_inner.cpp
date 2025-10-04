#include "svm/kernel.hpp"
#include "svm/model.hpp"
#include "util/profiler.hpp"
#include <Eigen/Dense>
#include <iostream>

using namespace sjs;

int main() {
  const int n = 5000, d = 64;
  Eigen::MatrixXd X = Eigen::MatrixXd::Random(n, d);
  RBFKernel k(0.1);
  SVMModel m;
  m.support_vectors = X.topRows(256);
  m.alpha_y = Eigen::VectorXd::Ones(256);
  m.bias = 0.0;

  double acc = 0.0;
  {
    SJS_PROFILE_SCOPE("decision batch");
    for (int i=0;i<1000;++i) {
      acc += m.decision(X.row(i).transpose(), k);
    }
  }
  std::cout << "acc=" << acc << "\n";
  return 0;
}
