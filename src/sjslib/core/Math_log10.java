package edu.stanford.cs.sjslib.core;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;
import edu.stanford.cs.svm.SVMMethod;

class Math_log10 extends SVMMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("Math.log10", "D");
      svm.pushDouble(Math.log(svm.popDouble()) / Math.log(10.0D));
   }
}
