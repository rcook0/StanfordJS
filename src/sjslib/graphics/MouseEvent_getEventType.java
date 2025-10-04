package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;

class MouseEvent_getEventType extends MouseEventMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("MouseEvent.getEventType", "");
      this.getMouseEvent(svm, receiver);
      svm.pushInteger(300);
   }
}
