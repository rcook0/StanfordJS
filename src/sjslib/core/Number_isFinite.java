package edu.stanford.cs.sjslib.core;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;
import edu.stanford.cs.svm.SVMMethod;

class Number_isFinite extends SVMMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("Number.isFinite", "D");
      double value = svm.popDouble();
      svm.pushBoolean(!Double.isNaN(value) && !Double.isInfinite(value));
   }
}
