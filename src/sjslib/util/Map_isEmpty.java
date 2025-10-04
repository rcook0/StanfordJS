package edu.stanford.cs.sjslib.util;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;

class Map_isEmpty extends MapMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("Map.isEmpty", "");
      svm.pushBoolean(this.getMap(svm, receiver).isEmpty());
   }
}
