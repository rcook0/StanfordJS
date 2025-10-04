package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.graphics.GImage;
import edu.stanford.cs.svm.SVM;

class GImage_getRed extends GImageMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("GImage.getRed", "I");
      svm.pushInteger(GImage.getRed(svm.popInteger()));
   }
}
