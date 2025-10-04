package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.graphics.GRectangle;
import edu.stanford.cs.svm.SVM;
import edu.stanford.cs.svm.SVMMethod;

abstract class GRectangleMethod extends SVMMethod {
   public GRectangle getGRectangle(SVM svm, Value receiver) {
      return receiver == null ? (GRectangle)svm.pop().getValue() : (GRectangle)receiver.getValue();
   }
}
