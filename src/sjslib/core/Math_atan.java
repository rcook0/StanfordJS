package edu.stanford.cs.sjslib.core;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;
import edu.stanford.cs.svm.SVMMethod;

class Math_atan extends SVMMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("Math.atan", "D");
      svm.pushDouble(Math.atan(svm.popDouble()));
   }
}
