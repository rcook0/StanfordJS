package edu.stanford.cs.sjslib.core;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;

class String_indexOf extends StringMethod {
   public void execute(SVM svm, Value receiver) {
      if (svm.getArgumentCount() == 2) {
         svm.checkSignature("String.indexOf", "SI");
         int k = svm.popInteger();
         String s = svm.popString();
         svm.pushInteger(this.getString(svm, receiver).indexOf(s, k));
      } else {
         svm.checkSignature("String.indexOf", "S");
         String s = svm.popString();
         svm.pushInteger(this.getString(svm, receiver).indexOf(s));
      }

   }
}
