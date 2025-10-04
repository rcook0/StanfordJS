package edu.stanford.cs.sjslib.core;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;
import edu.stanford.cs.svm.SVMMethod;

class Math_atan2 extends SVMMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("Math.atan2", "DD");
      double x = svm.popDouble();
      double y = svm.popDouble();
      svm.pushDouble(Math.atan2(y, x));
   }
}
