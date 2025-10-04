package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;

class GPoint_getY extends GPointMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("GPoint.getY", "");
      svm.pushDouble(this.getGPoint(svm, receiver).getY());
   }
}
