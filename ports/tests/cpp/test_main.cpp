#include "svm/trainer.hpp"
#include <cassert>
#include <iostream>

using namespace sjs;

int main() {
  Eigen::MatrixXd X(2,2); X << 1,0,  0,1;
  Eigen::VectorXi y(2); y << 1, -1;
  SMOTrainer t;
  LinearKernel k;
  SVMParams p;
  auto m = t.fit(X, y, k, p);
  double s = m.decision(Eigen::Vector2d(1,0), k);
  std::cout << "decision=" << s << "\n";
  return 0;
}
