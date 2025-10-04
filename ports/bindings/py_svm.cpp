#include <pybind11/pybind11.h>
#include <pybind11/eigen.h>
#include "svm/kernel.hpp"
#include "svm/model.hpp"
#include "svm/trainer.hpp"

namespace py = pybind11;
using namespace sjs;

PYBIND11_MODULE(sjs_svm, m) {
  py::class_<Kernel>(m, "Kernel");
  py::class_<LinearKernel, Kernel>(m, "LinearKernel")
    .def(py::init<>());
  py::class_<RBFKernel, Kernel>(m, "RBFKernel")
    .def(py::init<double>());

  py::class_<SVMModel>(m, "SVMModel")
    .def_readonly("support_vectors", &SVMModel::support_vectors)
    .def_readonly("alpha_y", &SVMModel::alpha_y)
    .def_readonly("bias", &SVMModel::bias)
    .def("decision", [](const SVMModel& self, const Eigen::VectorXd& x, const LinearKernel& k){
        return self.decision(x, k);
    })
    .def("decision", [](const SVMModel& self, const Eigen::VectorXd& x, const RBFKernel& k){
        return self.decision(x, k);
    });

  py::class_<SVMParams>(m, "SVMParams")
    .def(py::init<>())
    .def_readwrite("C", &SVMParams::C)
    .def_readwrite("tol", &SVMParams::tol)
    .def_readwrite("max_passes", &SVMParams::max_passes);

  py::class_<SMOTrainer>(m, "SMOTrainer")
    .def(py::init<>())
    .def("fit_linear", [](SMOTrainer& self, const Eigen::MatrixXd& X, const Eigen::VectorXi& y, const SVMParams& p){
        LinearKernel k; return self.fit(X, y, k, p);
    })
    .def("fit_rbf", [](SMOTrainer& self, const Eigen::MatrixXd& X, const Eigen::VectorXi& y, double gamma, const SVMParams& p){
        RBFKernel k(gamma); return self.fit(X, y, k, p);
    });
}
