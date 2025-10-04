package edu.stanford.cs.svm;

import edu.stanford.cs.exp.Value;

class Core_FALSE extends SVMConstant {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("Core.FALSE", "");
      svm.pushBoolean(false);
   }
}
