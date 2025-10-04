package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;

class KeyEvent_getKeyChar extends KeyEventMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("KeyEvent.getKeyChar", "");
      svm.pushInteger(this.getKeyEvent(svm, receiver).getKeyChar());
   }
}
