package edu.stanford.cs.sjslib.core;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;

class String_length extends StringMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("String.length", "");
      svm.pushInteger(this.getString(svm, receiver).length());
   }
}
