package edu.stanford.cs.sjslib.util;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;

class ActionEvent_getActionCommand extends ActionEventMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("ActionEvent.getActionCommand", "");
      svm.pushString(this.getActionEvent(svm, receiver).getActionCommand());
   }
}
