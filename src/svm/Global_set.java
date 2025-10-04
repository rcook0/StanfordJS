package edu.stanford.cs.svm;

import edu.stanford.cs.exp.Value;

class Global_set extends SVMMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("Global.set", "S");
      Value value = svm.pop();
      String key = svm.popString();
      svm.setGlobal(key, value);
   }
}
