package edu.stanford.cs.sjslib.core;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;
import edu.stanford.cs.svm.SVMMethod;

class Math_toDegrees extends SVMMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("Math.toDegrees", "D");
      svm.pushDouble(180.0D * svm.popDouble() / 3.141592653589793D);
   }
}
