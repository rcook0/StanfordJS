package edu.stanford.cs.sjslib.util;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;

class Set_isEmpty extends SetMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("Set.isEmpty", "");
      svm.pushBoolean(this.getSet(svm, receiver).isEmpty());
   }
}
