package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.graphics.GPoint;
import edu.stanford.cs.svm.SVM;

class GArc_getStartPoint extends GArcMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("GArc.getStartPoint", "");
      GPoint pt = this.getGArc(svm, receiver).getStartPoint();
      svm.push(Value.createObject(pt, "GPoint"));
   }
}
