package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;

class GLine_setEndPoint extends GLineMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("GLine.setEndPoint", "DD");
      double y = svm.popDouble();
      double x = svm.popDouble();
      this.getGLine(svm, receiver).setEndPoint(x, y);
      svm.push(Value.UNDEFINED);
   }
}
