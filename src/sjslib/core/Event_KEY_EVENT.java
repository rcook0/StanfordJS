package edu.stanford.cs.sjslib.core;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;
import edu.stanford.cs.svm.SVMConstant;

class Event_KEY_EVENT extends SVMConstant {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("Event.KEY_EVENT", "");
      svm.pushInteger(200);
   }
}
