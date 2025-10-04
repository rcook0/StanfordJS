package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.graphics.GRect;
import edu.stanford.cs.svm.SVM;
import edu.stanford.cs.svm.SVMMethod;

abstract class GRectMethod extends SVMMethod {
   public GRect getGRect(SVM svm, Value receiver) {
      return receiver == null ? (GRect)svm.pop().getValue() : (GRect)receiver.getValue();
   }
}
