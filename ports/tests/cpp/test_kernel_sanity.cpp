#include "svm/kernel.hpp"
#include "svm/model.hpp"
#include <cassert>
#include <cmath>
using namespace sjs;
int main() {
  LinearKernel lk;
  RBFKernel rk(0.5);
  SVMModel m;
  m.support_vectors = Eigen::MatrixXd(1,3);
  m.support_vectors << 0.5, -1.0, 4.0;
  m.alpha_y = Eigen::VectorXd::Ones(1);
  m.bias = 0.0;
  Eigen::Vector3d x(1.0, 2.0, 3.0);
  double dot = m.decision(x, lk);
  assert(std::abs(dot - (0.5*1.0 + -1.0*2.0 + 4.0*3.0)) < 1e-12);
  Eigen::Vector2d a(0.1,0.1), b(3.0,4.0);
  SVMModel m2; m2.support_vectors = Eigen::MatrixXd::Zero(1,2); m2.alpha_y = Eigen::VectorXd::Ones(1);
  assert(m2.decision(a, rk) > m2.decision(b, rk));
  return 0;
}
