package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.graphics.GImage;
import edu.stanford.cs.svm.SVM;
import edu.stanford.cs.svm.SVMMethod;

abstract class GImageMethod extends SVMMethod {
   public GImage getGImage(SVM svm, Value receiver) {
      return receiver == null ? (GImage)svm.pop().getValue() : (GImage)receiver.getValue();
   }
}
