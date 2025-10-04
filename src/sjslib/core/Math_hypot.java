package edu.stanford.cs.sjslib.core;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;
import edu.stanford.cs.svm.SVMMethod;

class Math_hypot extends SVMMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("Math.hypot", "DD");
      double y = svm.popDouble();
      double x = svm.popDouble();
      svm.pushDouble(Math.sqrt(x * x + y * y));
   }
}
