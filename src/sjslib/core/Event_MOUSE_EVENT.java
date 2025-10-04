package edu.stanford.cs.sjslib.core;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;
import edu.stanford.cs.svm.SVMConstant;

class Event_MOUSE_EVENT extends SVMConstant {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("Event.MOUSE_EVENT", "");
      svm.pushInteger(300);
   }
}
