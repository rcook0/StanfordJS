package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;

class GRectangle_getX extends GRectangleMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("GRectangle.getX", "");
      svm.pushDouble(this.getGRectangle(svm, receiver).getX());
   }
}
