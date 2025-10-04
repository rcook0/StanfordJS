#pragma once
#include <Eigen/Dense>
#include <cmath>

namespace sjs {

struct Kernel {
  virtual ~Kernel() = default;
  virtual double operator()(const Eigen::Ref<const Eigen::VectorXd>& x,
                            const Eigen::Ref<const Eigen::VectorXd>& z) const = 0;
};

struct LinearKernel final : Kernel {
  double operator()(const Eigen::Ref<const Eigen::VectorXd>& x,
                    const Eigen::Ref<const Eigen::VectorXd>& z) const override {
    return x.dot(z);
  }
};

struct RBFKernel final : Kernel {
  explicit RBFKernel(double gamma) : gamma_(gamma) {}
  double operator()(const Eigen::Ref<const Eigen::VectorXd>& x,
                    const Eigen::Ref<const Eigen::VectorXd>& z) const override {
    return std::exp(-gamma_ * (x - z).squaredNorm());
  }
 private:
  double gamma_;
};

} // namespace sjs
