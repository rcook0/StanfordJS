#include "svm/trainer.hpp"
#include "svm/io.hpp"


// Model I/O bindings
PYBIND11_MODULE(sjs_svm, m); // ensure module context exists if missing above
py::class_<ModelIO::KernelCfg>(m, "KernelCfg")
  .def(py::init<>())
  .def_readwrite("name", &ModelIO::KernelCfg::name)
  .def_readwrite("gamma", &ModelIO::KernelCfg::gamma);
m.def("save_model", [](const SVMModel& model, const ModelIO::KernelCfg& k, const std::string& path){
  ModelIO::save(model, k, path);
});
m.def("load_model", [](const std::string& path){
  SVMModel m; ModelIO::KernelCfg k; ModelIO::load(path, m, k);
  return std::make_pair(m, k);
});
