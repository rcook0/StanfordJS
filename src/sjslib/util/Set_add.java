package edu.stanford.cs.sjslib.util;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;

class Set_add extends SetMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("Set.add", "S");
      String str = svm.popString();
      this.getSet(svm, receiver).add(str);
      svm.push(Value.UNDEFINED);
   }
}
