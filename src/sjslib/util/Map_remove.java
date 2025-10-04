package edu.stanford.cs.sjslib.util;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;

class Map_remove extends MapMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("Map.remove", "S");
      String str = svm.popString();
      this.getMap(svm, receiver).remove(str);
      svm.push(Value.UNDEFINED);
   }
}
