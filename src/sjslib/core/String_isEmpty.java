package edu.stanford.cs.sjslib.core;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;

class String_isEmpty extends StringMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("String.isEmpty", "");
      svm.pushBoolean(this.getString(svm, receiver).length() == 0);
   }
}
