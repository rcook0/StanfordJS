package edu.stanford.cs.sjslib.core;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;

class String_compareTo extends StringMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("String.compareTo", "S");
      String s = svm.popString();
      svm.pushInteger(this.getString(svm, receiver).compareTo(s));
   }
}
