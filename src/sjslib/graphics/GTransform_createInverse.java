package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.graphics.GTransform;
import edu.stanford.cs.svm.SVM;

class GTransform_createInverse extends GTransformMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("GTransform.createInverse", "");
      GTransform t = this.getGTransform(svm, receiver).createInverse();
      svm.push(Value.createObject(t, "GTransform"));
   }
}
