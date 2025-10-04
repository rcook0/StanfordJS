package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;

class GTransform_translate extends GTransformMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("GTransform.translate", "DD");
      double ty = svm.popDouble();
      double tx = svm.popDouble();
      this.getGTransform(svm, receiver).translate(tx, ty);
      svm.push(Value.UNDEFINED);
   }
}
