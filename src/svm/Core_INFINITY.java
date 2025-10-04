package edu.stanford.cs.svm;

import edu.stanford.cs.exp.Value;

class Core_INFINITY extends SVMConstant {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("Core.INFINITY", "");
      svm.push(Value.createDouble(Double.POSITIVE_INFINITY));
   }
}
