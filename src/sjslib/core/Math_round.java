package edu.stanford.cs.sjslib.core;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;
import edu.stanford.cs.svm.SVMMethod;

class Math_round extends SVMMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("Math.round", "D");
      svm.pushInteger((int)Math.round(svm.popDouble()));
   }
}
