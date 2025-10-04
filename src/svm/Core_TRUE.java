package edu.stanford.cs.svm;

import edu.stanford.cs.exp.Value;

class Core_TRUE extends SVMConstant {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("Core.TRUE", "");
      svm.pushBoolean(true);
   }
}
