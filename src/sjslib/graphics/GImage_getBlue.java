package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.graphics.GImage;
import edu.stanford.cs.svm.SVM;

class GImage_getBlue extends GImageMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("GImage.getBlue", "I");
      svm.pushInteger(GImage.getBlue(svm.popInteger()));
   }
}
