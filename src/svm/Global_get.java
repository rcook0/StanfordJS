package edu.stanford.cs.svm;

import edu.stanford.cs.exp.Value;

class Global_get extends SVMMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("Global.get", "S");
      svm.push(svm.getGlobal(svm.popString()));
   }
}
