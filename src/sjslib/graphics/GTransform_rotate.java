package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;

class GTransform_rotate extends GTransformMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("GTransform.rotate", "D");
      double theta = svm.popDouble();
      this.getGTransform(svm, receiver).rotate(theta);
      svm.push(Value.UNDEFINED);
   }
}
