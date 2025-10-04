#pragma once
#include <unordered_map>
#include <list>
#include <utility>
#include <cstddef>

namespace sjs {

struct PairKey { int a, b; };
struct PairHash {
  std::size_t operator()(const PairKey& k) const noexcept {
    int x = k.a < k.b ? k.a : k.b;
    int y = k.a < k.b ? k.b : k.a;
    return std::hash<long long>{}((static_cast<long long>(x) << 32) ^ static_cast<unsigned long long>(y));
  }
};
struct PairEq {
  bool operator()(const PairKey& p, const PairKey& q) const noexcept {
    return (p.a==q.a && p.b==q.b) || (p.a==q.b && p.b==q.a);
  }
};

class KernelCache {
 public:
  explicit KernelCache(std::size_t capacity = 8192) : cap_(capacity) {}
  bool get(int i, int j, double& out) {
    PairKey k{i,j};
    auto it = map_.find(k);
    if (it == map_.end()) return false;
    touch(it->second.it);
    out = it->second.value;
    return true;
  }
  void put(int i, int j, double value) {
    PairKey k{i,j};
    auto it = map_.find(k);
    if (it != map_.end()) {
      it->second.value = value;
      touch(it->second.it);
      return;
    }
    if (list_.size() >= cap_) {
      auto ev = list_.back(); list_.pop_back();
      map_.erase(ev);
    }
    list_.push_front(k);
    map_[k] = Node{value, list_.begin()};
  }
 private:
  struct Node { double value; std::list<PairKey>::iterator it; };
  void touch(std::list<PairKey>::iterator it) { list_.splice(list_.begin(), list_, it); }
  std::size_t cap_;
  std::list<PairKey> list_;
  std::unordered_map<PairKey, Node, PairHash, PairEq> map_;
};

} // namespace sjs
