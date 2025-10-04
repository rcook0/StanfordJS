package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.graphics.GImage;
import edu.stanford.cs.svm.SVM;

class GImage_createRGBPixel extends GImageMethod {
   public void execute(SVM svm, Value receiver) {
      int a;
      int b;
      int g;
      if (svm.getArgumentCount() == 4) {
         svm.checkSignature("GImage.createRGBPixel", "IIII");
         a = svm.popInteger();
         b = svm.popInteger();
         g = svm.popInteger();
         int r = svm.popInteger();
         svm.pushInteger(GImage.createRGBPixel(r, g, b, a));
      } else {
         svm.checkSignature("GImage.createRGBPixel", "III");
         a = svm.popInteger();
         b = svm.popInteger();
         g = svm.popInteger();
         svm.pushInteger(GImage.createRGBPixel(g, b, a));
      }

   }
}
