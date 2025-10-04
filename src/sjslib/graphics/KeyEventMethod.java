package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;
import edu.stanford.cs.svm.SVMMethod;
import java.awt.event.KeyEvent;

abstract class KeyEventMethod extends SVMMethod {
   public KeyEvent getKeyEvent(SVM svm, Value receiver) {
      return receiver == null ? (KeyEvent)svm.pop().getValue() : (KeyEvent)receiver.getValue();
   }
}
