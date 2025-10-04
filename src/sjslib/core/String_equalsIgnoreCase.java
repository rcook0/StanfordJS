package edu.stanford.cs.sjslib.core;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;

class String_equalsIgnoreCase extends StringMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("String.equalsIgnoreCase", "S");
      String s = svm.popString();
      svm.pushBoolean(this.getString(svm, receiver).equalsIgnoreCase(s));
   }
}
