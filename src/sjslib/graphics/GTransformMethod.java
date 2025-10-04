package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.graphics.GTransform;
import edu.stanford.cs.svm.SVM;
import edu.stanford.cs.svm.SVMMethod;

abstract class GTransformMethod extends SVMMethod {
   public GTransform getGTransform(SVM svm, Value receiver) {
      return receiver == null ? (GTransform)svm.pop().getValue() : (GTransform)receiver.getValue();
   }
}
