package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.graphics.GPolygon;
import edu.stanford.cs.svm.SVM;
import edu.stanford.cs.svm.SVMMethod;

abstract class GPolygonMethod extends SVMMethod {
   public GPolygon getGPolygon(SVM svm, Value receiver) {
      return receiver == null ? (GPolygon)svm.pop().getValue() : (GPolygon)receiver.getValue();
   }
}
