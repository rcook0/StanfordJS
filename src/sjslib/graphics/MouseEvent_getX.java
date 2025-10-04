package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;

class MouseEvent_getX extends MouseEventMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("MouseEvent.getX", "");
      svm.pushDouble((double)this.getMouseEvent(svm, receiver).getX());
   }
}
