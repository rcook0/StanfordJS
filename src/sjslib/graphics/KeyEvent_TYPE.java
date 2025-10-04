package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;
import edu.stanford.cs.svm.SVMConstant;

class KeyEvent_TYPE extends SVMConstant {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("KeyEvent.TYPE", "");
      svm.pushInteger(200);
   }
}
