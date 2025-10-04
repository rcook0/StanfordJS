package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;

class GTransform_scale extends GTransformMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("GTransform.scale", "DD");
      double sy = svm.popDouble();
      double sx = svm.popDouble();
      this.getGTransform(svm, receiver).scale(sx, sy);
      svm.push(Value.UNDEFINED);
   }
}
