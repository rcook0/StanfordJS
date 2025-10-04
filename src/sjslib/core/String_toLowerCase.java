package edu.stanford.cs.sjslib.core;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;

class String_toLowerCase extends StringMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("String.toLowerCase", "");
      svm.pushString(this.getString(svm, receiver).toLowerCase());
   }
}
