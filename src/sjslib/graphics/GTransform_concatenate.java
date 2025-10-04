package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.graphics.GTransform;
import edu.stanford.cs.svm.SVM;

class GTransform_concatenate extends GTransformMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("GTransform.concatenate", "O");
      GTransform t = (GTransform)svm.pop().getValue();
      this.getGTransform(svm, receiver).concatenate(t);
      svm.push(Value.UNDEFINED);
   }
}
