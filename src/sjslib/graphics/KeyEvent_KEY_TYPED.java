package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;
import edu.stanford.cs.svm.SVMConstant;

class KeyEvent_KEY_TYPED extends SVMConstant {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("KeyEvent.KEY_TYPED", "");
      svm.pushInteger(201);
   }
}
