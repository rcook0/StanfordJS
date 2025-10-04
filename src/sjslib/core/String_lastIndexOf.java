package edu.stanford.cs.sjslib.core;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;

class String_lastIndexOf extends StringMethod {
   public void execute(SVM svm, Value receiver) {
      if (svm.getArgumentCount() == 2) {
         svm.checkSignature("String.lastIndexOf", "SI");
         int k = svm.popInteger();
         String s = svm.popString();
         svm.pushInteger(this.getString(svm, receiver).lastIndexOf(s, k));
      } else {
         svm.checkSignature("String.lastIndexOf", "S");
         String s = svm.popString();
         svm.pushInteger(this.getString(svm, receiver).lastIndexOf(s));
      }

   }
}
