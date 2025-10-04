package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;
import edu.stanford.cs.svm.SVMConstant;

class MouseEvent_MOUSE_RELEASED extends SVMConstant {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("MouseEvent.MOUSE_RELEASED", "");
      svm.pushInteger(305);
   }
}
