package edu.stanford.cs.sjslib.core;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;
import edu.stanford.cs.svm.SVMMethod;

class Math_pow extends SVMMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("Math.pow", "DD");
      double d2 = svm.popDouble();
      double d1 = svm.popDouble();
      svm.pushDouble(Math.pow(d1, d2));
   }
}
