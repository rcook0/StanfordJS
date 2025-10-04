package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;

class GTransform_getTranslateX extends GTransformMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("GTransform.getTranslateX", "");
      svm.pushDouble(this.getGTransform(svm, receiver).getTranslateX());
   }
}
