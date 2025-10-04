package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.graphics.GRoundRect;
import edu.stanford.cs.svm.SVM;
import edu.stanford.cs.svm.SVMMethod;

abstract class GRoundRectMethod extends SVMMethod {
   public GRoundRect getGRoundRect(SVM svm, Value receiver) {
      return receiver == null ? (GRoundRect)svm.pop().getValue() : (GRoundRect)receiver.getValue();
   }
}
