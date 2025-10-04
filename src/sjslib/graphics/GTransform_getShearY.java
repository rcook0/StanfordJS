package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;

class GTransform_getShearY extends GTransformMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("GTransform.getShearY", "");
      svm.pushDouble(this.getGTransform(svm, receiver).getShearY());
   }
}
