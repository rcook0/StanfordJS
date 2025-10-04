package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;

class GTransform_setTransform extends GTransformMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("GTransform.setTransform", "DDDDDD");
      double ty = svm.popDouble();
      double tx = svm.popDouble();
      double d = svm.popDouble();
      double c = svm.popDouble();
      double b = svm.popDouble();
      double a = svm.popDouble();
      this.getGTransform(svm, receiver).setTransform(a, b, c, d, tx, ty);
      svm.push(Value.UNDEFINED);
   }
}
