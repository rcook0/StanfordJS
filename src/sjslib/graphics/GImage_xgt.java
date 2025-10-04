package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.graphics.GImage;
import edu.stanford.cs.svm.SVM;

class GImage_xgt extends GImageMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("GImage.xgt", "*");
      GImage image = (GImage)svm.pop().getValue();
      int[][] array = image.getPixelArray();
      int height = array.length;
      int width = array[0].length;

      for(int i = 0; i < height; ++i) {
         for(int j = 0; j < width; ++j) {
            int pixel = array[i][j];
            int r = GImage.getRed(pixel);
            int g = GImage.getGreen(pixel);
            int b = GImage.getBlue(pixel);
            if (g > 2 * Math.max(r, b)) {
               array[i][j] = 0;
            }
         }
      }

      svm.push(Value.createObject(new GImage(array), "GImage"));
   }
}
