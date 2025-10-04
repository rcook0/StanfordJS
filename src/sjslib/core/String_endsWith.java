package edu.stanford.cs.sjslib.core;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;

class String_endsWith extends StringMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("String.endsWith", "S");
      String s = svm.popString();
      svm.pushBoolean(this.getString(svm, receiver).endsWith(s));
   }
}
