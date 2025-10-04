package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.graphics.GLabel;
import edu.stanford.cs.svm.SVM;
import edu.stanford.cs.svm.SVMMethod;

abstract class GLabelMethod extends SVMMethod {
   public GLabel getGLabel(SVM svm, Value receiver) {
      return receiver == null ? (GLabel)svm.pop().getValue() : (GLabel)receiver.getValue();
   }
}
