package edu.stanford.cs.sjslib.core;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;
import edu.stanford.cs.svm.SVMMethod;

class Number_parseInt extends SVMMethod {
   public void execute(SVM svm, Value receiver) {
      try {
         if (svm.getArgumentCount() == 2) {
            svm.checkSignature("Number.parseInt", "SI");
            int base = svm.popInteger();
            svm.pushInteger(Integer.parseInt(svm.popString(), base));
         } else {
            svm.checkSignature("Number.parseInt", "S");
            svm.pushInteger(Integer.parseInt(svm.popString()));
         }
      } catch (NumberFormatException var4) {
         svm.pushDouble(Double.NaN);
      }

   }
}
