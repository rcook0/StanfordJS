package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.graphics.GOval;
import edu.stanford.cs.svm.SVM;
import edu.stanford.cs.svm.SVMMethod;

abstract class GOvalMethod extends SVMMethod {
   public GOval getGOval(SVM svm, Value receiver) {
      return receiver == null ? (GOval)svm.pop().getValue() : (GOval)receiver.getValue();
   }
}
