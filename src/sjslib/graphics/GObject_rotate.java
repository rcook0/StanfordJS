package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;

class GObject_rotate extends GObjectMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("GObject.rotate", "D");
      double theta = svm.popDouble();
      this.getGObject(svm, receiver).rotate(theta);
      svm.push(Value.UNDEFINED);
   }
}
