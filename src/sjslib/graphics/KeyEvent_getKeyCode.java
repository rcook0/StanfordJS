package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;

class KeyEvent_getKeyCode extends KeyEventMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("KeyEvent.getKeyCode", "");
      svm.pushInteger(this.getKeyEvent(svm, receiver).getKeyCode());
   }
}
