package edu.stanford.cs.sjslib.util;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;

class ActionEvent_getSource extends ActionEventMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("ActionEvent.getSource", "");
      Object source = this.getActionEvent(svm, receiver).getSource();
      Value v = new Value(79, source);
      String className = source.getClass().getName();
      className = className.substring(className.lastIndexOf(".") + 1);
      v.setClassName(className);
      svm.push(v);
   }
}
