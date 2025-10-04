package edu.stanford.cs.sjslib.core;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;

class String_concat extends StringMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("String.concat", "**");
      String s = svm.popString();
      svm.pushString(this.getString(svm, receiver).concat(s));
   }
}
