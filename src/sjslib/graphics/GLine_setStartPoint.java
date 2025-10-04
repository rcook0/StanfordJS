package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;

class GLine_setStartPoint extends GLineMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("GLine.setStartPoint", "DD");
      double y = svm.popDouble();
      double x = svm.popDouble();
      this.getGLine(svm, receiver).setStartPoint(x, y);
      svm.push(Value.UNDEFINED);
   }
}
