package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.graphics.GPoint;
import edu.stanford.cs.svm.SVM;

class GLine_getEndPoint extends GLineMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("GLine.getEndPoint", "");
      GPoint pt = this.getGLine(svm, receiver).getEndPoint();
      svm.push(Value.createObject(pt, "GPoint"));
   }
}
