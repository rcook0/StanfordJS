package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;

class GPolygon_isFilled extends GPolygonMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("GPolygon.isFilled", "");
      svm.pushBoolean(this.getGPolygon(svm, receiver).isFilled());
   }
}
