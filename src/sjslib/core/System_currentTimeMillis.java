package edu.stanford.cs.sjslib.core;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;
import edu.stanford.cs.svm.SVMMethod;

class System_currentTimeMillis extends SVMMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("System.currentTimeMillis", "");
      svm.pushDouble((double)System.currentTimeMillis());
   }
}
