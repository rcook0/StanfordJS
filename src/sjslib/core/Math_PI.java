package edu.stanford.cs.sjslib.core;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;
import edu.stanford.cs.svm.SVMConstant;

class Math_PI extends SVMConstant {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("Math.PI", "");
      svm.pushDouble(3.141592653589793D);
   }
}
