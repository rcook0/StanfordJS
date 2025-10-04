package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;
import edu.stanford.cs.svm.SVMMethod;
import java.awt.event.MouseEvent;

abstract class MouseEventMethod extends SVMMethod {
   public MouseEvent getMouseEvent(SVM svm, Value receiver) {
      return receiver == null ? (MouseEvent)svm.pop().getValue() : (MouseEvent)receiver.getValue();
   }
}
