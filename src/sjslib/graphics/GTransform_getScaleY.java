package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;

class GTransform_getScaleY extends GTransformMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("GTransform.getScaleY", "");
      svm.pushDouble(this.getGTransform(svm, receiver).getScaleY());
   }
}
