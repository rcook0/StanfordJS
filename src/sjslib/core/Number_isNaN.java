package edu.stanford.cs.sjslib.core;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;
import edu.stanford.cs.svm.SVMMethod;

class Number_isNaN extends SVMMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("Number.isNaN", "D");
      svm.pushBoolean(Double.isNaN(svm.popDouble()));
   }
}
