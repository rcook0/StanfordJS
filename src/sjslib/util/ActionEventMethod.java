package edu.stanford.cs.sjslib.util;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;
import edu.stanford.cs.svm.SVMMethod;
import java.awt.event.ActionEvent;

abstract class ActionEventMethod extends SVMMethod {
   public ActionEvent getActionEvent(SVM svm, Value receiver) {
      return receiver == null ? (ActionEvent)svm.pop().getValue() : (ActionEvent)receiver.getValue();
   }
}
