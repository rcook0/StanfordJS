package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;

class GObject_shear extends GObjectMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("GObject.shear", "DD");
      double sy = svm.popDouble();
      double sx = svm.popDouble();
      this.getGObject(svm, receiver).shear(sx, sy);
      svm.push(Value.UNDEFINED);
   }
}
