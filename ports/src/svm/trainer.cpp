#include "svm/trainer.hpp"
#include "svm/kernel.hpp"
#include <Eigen/Dense>
#include <algorithm>
#include <cmath>

namespace sjs {

static inline double clip(double v, double L, double H) {
  if (v < L) return L;
  if (v > H) return H;
  return v;
}

template <typename K>
SVMModel SMOTrainer::fit(const Eigen::MatrixXd& X, const Eigen::VectorXi& y,
                         const K& kernel, const SVMParams& p) {
  const int n = (int)X.rows();
  Eigen::VectorXd alpha = Eigen::VectorXd::Zero(n);
  double b = 0.0;

  auto f = [&](int i) {
    double s = -b;
    for (int t=0; t<n; ++t) if (alpha(t) != 0.0) {
      s += alpha(t) * y(t) * kernel(X.row(t).transpose(), X.row(i).transpose());
    }
    return s;
  };

  int passes = 0;
  while (passes < p.max_passes) {
    int num_changed = 0;
    for (int i = 0; i < n; ++i) {
      double Ei = f(i) - y(i);
      bool Ki = ((y(i)*Ei < -p.tol && alpha(i) < p.C) || (y(i)*Ei > p.tol && alpha(i) > 0));
      if (!Ki) continue;

      int j = -1; double best = -1.0, Ej = 0.0;
      for (int t = 0; t < n; ++t) if (t != i) {
        double Et = f(t) - y(t);
        double score = std::abs(Ei - Et);
        if (score > best) { best = score; j=t; Ej=Et; }
      }
      if (j < 0) continue;

      double ai_old = alpha(i), aj_old = alpha(j);

      double L, H;
      if (y(i) != y(j)) { L = std::max(0.0, aj_old - ai_old); H = std::min((double)p.C, (double)(p.C + aj_old - ai_old)); }
      else              { L = std::max(0.0, ai_old + aj_old - p.C); H = std::min((double)p.C, (double)(ai_old + aj_old)); }
      if (L == H) continue;

      double Kii = kernel(X.row(i).transpose(), X.row(i).transpose());
      double Kjj = kernel(X.row(j).transpose(), X.row(j).transpose());
      double Kij = kernel(X.row(i).transpose(), X.row(j).transpose());
      double eta = Kii + Kjj - 2.0 * Kij;
      if (eta <= 0) continue;

      double aj = aj_old + y(j) * (Ei - Ej) / eta;
      aj = clip(aj, L, H);
      if (std::abs(aj - aj_old) < 1e-12) continue;

      double ai = ai_old + y(i) * y(j) * (aj_old - aj);

      double b1 = Ei + y(i)*(ai - ai_old)*Kii + y(j)*(aj - aj_old)*Kij + b;
      double b2 = Ej + y(i)*(ai - ai_old)*Kij + y(j)*(aj - aj_old)*Kjj + b;
      if (0.0 < ai && ai < p.C) b = b1;
      else if (0.0 < aj && aj < p.C) b = b2;
      else b = 0.5 * (b1 + b2);

      alpha(i) = ai; alpha(j) = aj;
      ++num_changed;
    }
    passes = (num_changed == 0) ? passes + 1 : 0;
  }

  int sv = 0; for (int i=0;i<n;++i) if (alpha(i) != 0.0) ++sv;
  SVMModel m;
  if (sv == 0) { m.support_vectors = X.topRows(0); m.alpha_y = Eigen::VectorXd::Zero(0); m.bias = b; return m; }
  m.support_vectors.resize(sv, X.cols());
  m.alpha_y.resize(sv);
  for (int i=0, k=0; i<n; ++i) if (alpha(i) != 0.0) {
    m.support_vectors.row(k) = X.row(i);
    m.alpha_y(k) = alpha(i) * y(i);
    ++k;
  }
  m.bias = b;
  return m;
}

template SVMModel SMOTrainer::fit(const Eigen::MatrixXd&, const Eigen::VectorXi&, const LinearKernel&, const SVMParams&);
template SVMModel SMOTrainer::fit(const Eigen::MatrixXd&, const Eigen::VectorXi&, const RBFKernel&,   const SVMParams&);

} // namespace sjs
