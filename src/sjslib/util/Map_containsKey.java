package edu.stanford.cs.sjslib.util;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;

class Map_containsKey extends MapMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("Map.containsKey", "S");
      String str = svm.popString();
      svm.pushBoolean(this.getMap(svm, receiver).containsKey(str));
   }
}
