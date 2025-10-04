package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.graphics.GPoint;
import edu.stanford.cs.svm.SVM;
import edu.stanford.cs.svm.SVMMethod;

abstract class GPointMethod extends SVMMethod {
   public GPoint getGPoint(SVM svm, Value receiver) {
      return receiver == null ? (GPoint)svm.pop().getValue() : (GPoint)receiver.getValue();
   }
}
