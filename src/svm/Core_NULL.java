package edu.stanford.cs.svm;

import edu.stanford.cs.exp.Value;

class Core_NULL extends SVMConstant {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("Core.NULL", "");
      svm.push(Value.NULL);
   }
}
