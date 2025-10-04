package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;

class GRectangle_getY extends GRectangleMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("GRectangle.getY", "");
      svm.pushDouble(this.getGRectangle(svm, receiver).getY());
   }
}
