package edu.stanford.cs.sjslib.util;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;

class ActionEvent_getEventType extends ActionEventMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("ActionEvent.getEventType", "");
      this.getActionEvent(svm, receiver);
      svm.pushInteger(100);
   }
}
