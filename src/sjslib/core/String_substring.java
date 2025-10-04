package edu.stanford.cs.sjslib.core;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;

class String_substring extends StringMethod {
   public void execute(SVM svm, Value receiver) {
      int p2;
      if (svm.getArgumentCount() == 1) {
         svm.checkSignature("String.substring", "I");
         p2 = svm.popInteger();
         svm.pushString(this.getString(svm, receiver).substring(p2));
      } else {
         svm.checkSignature("String.substring", "II");
         p2 = Math.max(svm.popInteger(), 0);
         int p1 = Math.max(svm.popInteger(), 0);
         String str = this.getString(svm, receiver);
         p1 = Math.min(p1, str.length());
         p2 = Math.min(p2, str.length());
         if (p1 > p2) {
            int tmp = p1;
            p1 = p2;
            p2 = tmp;
         }

         svm.pushString(str.substring(p1, p2));
      }

   }
}
