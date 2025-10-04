package edu.stanford.cs.svm;

import edu.stanford.cs.exp.Value;

class Core_this extends SVMConstant {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("Core.this", "");
      svm.push(svm.getCurrentFrame().getThis());
   }
}
