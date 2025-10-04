package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;
import edu.stanford.cs.svm.SVMConstant;

class MouseEvent_MOUSE_CLICKED extends SVMConstant {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("MouseEvent.MOUSE_CLICKED", "");
      svm.pushInteger(301);
   }
}
