package edu.stanford.cs.sjslib.util;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;

class ActionEvent_getID extends ActionEventMethod {
   public void execute(SVM svm, Value receiver) {
      svm.pushInteger(101);
   }
}
