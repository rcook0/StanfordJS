package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.graphics.GPoint;
import edu.stanford.cs.svm.SVM;

class GArc_getEndPoint extends GArcMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("GArc.getEndPoint", "");
      GPoint pt = this.getGArc(svm, receiver).getEndPoint();
      svm.push(Value.createObject(pt, "GPoint"));
   }
}
