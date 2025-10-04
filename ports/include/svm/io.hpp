#pragma once
#include "model.hpp"
#include "kernel.hpp"
#include <string>
#include <fstream>
#include <sstream>
#include <vector>

namespace sjs {

struct ModelIO {
  struct KernelCfg {
    std::string name;   // "linear" | "rbf"
    double gamma = 0.0; // for rbf
  };

  static void save(const SVMModel& m, const KernelCfg& k, const std::string& path) {
    std::ofstream out(path, std::ios::binary);
    out.setf(std::ios::fmtflags(0), std::ios::floatfield);
    out.precision(17);
    out << "{\n";
    out << "  \"kernel\": \"" << k.name << "\",\n";
    if (k.name == "rbf") out << "  \"gamma\": " << k.gamma << ",\n";
    out << "  \"bias\": " << m.bias << ",\n";
    out << "  \"support_vectors\": [";
    for (int i = 0; i < m.support_vectors.rows(); ++i) {
      if (i) out << ", ";
      out << "[";
      for (int j = 0; j < m.support_vectors.cols(); ++j) {
        if (j) out << ", ";
        out << m.support_vectors(i, j);
      }
      out << "]";
    }
    out << "],\n  \"alpha_y\": [";
    for (int i = 0; i < m.alpha_y.size(); ++i) {
      if (i) out << ", ";
      out << m.alpha_y(i);
    }
    out << "]\n}\n";
  }

  static void load(const std::string& path, SVMModel& m, KernelCfg& k) {
    std::ifstream in(path, std::ios::binary);
    std::string s((std::istreambuf_iterator<char>(in)), {});
    auto get = [&](const std::string& key) -> std::string {
      auto p = s.find("\"" + key + "\"");
      if (p == std::string::npos) return {};
      p = s.find(':', p); auto q = s.find_first_of(",}\n", p+1);
      return s.substr(p+1, q-p-1);
    };
    auto trim = [](std::string v) {
      auto a = v.find_first_not_of(" \t\r\n\""); auto b = v.find_last_not_of(" \t\r\n\"");
      return (a==std::string::npos) ? std::string() : v.substr(a, b-a+1);
    };
    k.name = trim(get("kernel"));
    if (k.name == "rbf") k.gamma = std::stod(trim(get("gamma")));
    m.bias = std::stod(trim(get("bias")));

    { // alpha_y
      auto p = s.find("\"alpha_y\"");
      p = s.find('[', p); auto q = s.find(']', p);
      std::stringstream ss(s.substr(p+1, q-p-1));
      std::vector<double> vals; std::string tok;
      while (std::getline(ss, tok, ',')) vals.push_back(std::stod(trim(tok)));
      m.alpha_y.resize((int)vals.size());
      for (int i=0;i<(int)vals.size();++i) m.alpha_y(i)=vals[i];
    }
    { // support_vectors
      auto p = s.find("\"support_vectors\"");
      p = s.find('[', p); auto q = s.rfind(']');
      std::string body = s.substr(p+1, q-p-1);
      int rows = 0, cols = -1;
      for (size_t i=0;i<body.size();++i) if (body[i]=='[') ++rows;
      auto r0b = body.find('['); auto r0e = body.find(']');
      std::stringstream ss0(body.substr(r0b+1, r0e-r0b-1)); std::string tok;
      int c=0; while (std::getline(ss0, tok, ',')) ++c; cols = c;
      m.support_vectors.resize(rows, cols);
      size_t pos = 0; int r=0;
      while (true) {
        auto lb = body.find('[', pos); if (lb==std::string::npos) break;
        auto rb = body.find(']', lb+1);
        std::stringstream row(body.substr(lb+1, rb-lb-1));
        int cj=0; while (std::getline(row, tok, ',')) m.support_vectors(r, cj++) = std::stod(trim(tok));
        ++r; pos = rb+1;
      }
    }
  }
};

} // namespace sjs
