package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;

class GObject_setLineWidth extends GObjectMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("GObject.setLineWidth", "D");
      double width = svm.popDouble();
      this.getGObject(svm, receiver).setLineWidth(width);
      svm.push(Value.UNDEFINED);
   }
}
