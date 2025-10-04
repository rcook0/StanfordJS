package edu.stanford.cs.sjslib.core;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;

class String_trim extends StringMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("String.trim", "");
      svm.pushString(this.getString(svm, receiver).trim());
   }
}
