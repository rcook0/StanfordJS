package edu.stanford.cs.sjslib.core;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;

class String_contains extends StringMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("String.contains", "S");
      String s = svm.popString();
      svm.pushBoolean(this.getString(svm, receiver).indexOf(s) >= 0);
   }
}
