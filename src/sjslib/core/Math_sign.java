package edu.stanford.cs.sjslib.core;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;
import edu.stanford.cs.svm.SVMMethod;

class Math_sign extends SVMMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("Math.sign", "D");
      double d = svm.popDouble();
      if (d == 0.0D) {
         svm.pushInteger(0);
      } else if (d < 0.0D) {
         svm.pushInteger(-1);
      } else {
         svm.pushInteger(1);
      }

   }
}
