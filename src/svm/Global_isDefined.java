package edu.stanford.cs.svm;

import edu.stanford.cs.exp.Value;

class Global_isDefined extends SVMMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("Global.isDefined", "S");
      svm.pushBoolean(svm.isGlobal(svm.popString()));
   }
}
