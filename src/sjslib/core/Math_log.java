package edu.stanford.cs.sjslib.core;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;
import edu.stanford.cs.svm.SVMMethod;

class Math_log extends SVMMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("Math.log", "D");
      svm.pushDouble(Math.log(svm.popDouble()));
   }
}
