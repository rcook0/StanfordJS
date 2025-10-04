package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.graphics.GImage;
import edu.stanford.cs.svm.SVM;

class GImage_getAlpha extends GImageMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("GImage.getAlpha", "I");
      svm.pushInteger(GImage.getAlpha(svm.popInteger()));
   }
}
