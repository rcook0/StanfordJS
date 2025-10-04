package edu.stanford.cs.sjslib.util;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;

class Map_clear extends MapMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("Map.clear", "");
      this.getMap(svm, receiver).clear();
      svm.push(Value.UNDEFINED);
   }
}
