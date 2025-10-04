package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;

class KeyEvent_getEventType extends KeyEventMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("KeyEvent.getEventType", "");
      this.getKeyEvent(svm, receiver);
      svm.pushInteger(200);
   }
}
