package edu.stanford.cs.sjslib.core;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;

class String_compareToIgnoreCase extends StringMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("String.compareToIgnoreCase", "S");
      String s = svm.popString().toUpperCase();
      svm.pushInteger(this.getString(svm, receiver).toUpperCase().compareTo(s));
   }
}
