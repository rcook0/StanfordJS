package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.graphics.GImage;
import edu.stanford.cs.svm.SVM;

class GImage_getGreen extends GImageMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("GImage.getGreen", "I");
      svm.pushInteger(GImage.getGreen(svm.popInteger()));
   }
}
