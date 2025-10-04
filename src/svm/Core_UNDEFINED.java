package edu.stanford.cs.svm;

import edu.stanford.cs.exp.Value;

class Core_UNDEFINED extends SVMConstant {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("Core.UNDEFINED", "");
      svm.push(Value.UNDEFINED);
   }
}
