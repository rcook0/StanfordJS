package edu.stanford.cs.sjslib.core;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;

class String_charCodeAt extends StringMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("String.charCodeAt", "I");
      int k = svm.popInteger();
      svm.pushInteger(this.getString(svm, receiver).charAt(k));
   }
}
