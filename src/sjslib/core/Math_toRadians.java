package edu.stanford.cs.sjslib.core;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;
import edu.stanford.cs.svm.SVMMethod;

class Math_toRadians extends SVMMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("Math.toRadians", "D");
      svm.pushDouble(3.141592653589793D * svm.popDouble() / 180.0D);
   }
}
