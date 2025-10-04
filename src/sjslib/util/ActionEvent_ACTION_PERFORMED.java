package edu.stanford.cs.sjslib.util;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;
import edu.stanford.cs.svm.SVMConstant;

class ActionEvent_ACTION_PERFORMED extends SVMConstant {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("ActionEvent.ACTION_PERFORMED", "");
      svm.pushInteger(101);
   }
}
