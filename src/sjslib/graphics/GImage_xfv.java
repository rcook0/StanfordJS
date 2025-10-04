package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.graphics.GImage;
import edu.stanford.cs.svm.SVM;

class GImage_xfv extends GImageMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("GImage.xfv", "*");
      GImage image = (GImage)svm.pop().getValue();
      int[][] array = image.getPixelArray();
      int height = array.length;

      for(int p1 = 0; p1 < height / 2; ++p1) {
         int p2 = height - p1 - 1;
         int[] temp = array[p1];
         array[p1] = array[p2];
         array[p2] = temp;
      }

      svm.push(Value.createObject(new GImage(array), "GImage"));
   }
}
