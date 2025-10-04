package edu.stanford.cs.sjslib.core;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;
import edu.stanford.cs.svm.SVMConstant;

class Math_E extends SVMConstant {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("Math.E", "");
      svm.pushDouble(2.718281828459045D);
   }
}
