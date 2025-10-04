package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.graphics.GImage;
import edu.stanford.cs.svm.SVM;

class GImage_xfh extends GImageMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("GImage.xfh", "*");
      GImage image = (GImage)svm.pop().getValue();
      int[][] array = image.getPixelArray();
      int height = array.length;
      int width = array[0].length;

      for(int i = 0; i < height; ++i) {
         for(int j = 0; j < width / 2; ++j) {
            int k = width - j - 1;
            int temp = array[i][j];
            array[i][j] = array[i][k];
            array[i][k] = temp;
         }
      }

      svm.push(Value.createObject(new GImage(array), "GImage"));
   }
}
