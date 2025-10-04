package edu.stanford.cs.sjslib.core;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;
import edu.stanford.cs.svm.SVMMethod;

class Math_sqrt extends SVMMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("Math.sqrt", "D");
      svm.pushDouble(Math.sqrt(svm.popDouble()));
   }
}
