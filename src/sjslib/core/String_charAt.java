package edu.stanford.cs.sjslib.core;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;

class String_charAt extends StringMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("String.charAt", "I");
      int k = svm.popInteger();
      svm.pushString(this.getString(svm, receiver).substring(k, k + 1));
   }
}
