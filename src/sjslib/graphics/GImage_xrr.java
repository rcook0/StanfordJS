package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.graphics.GImage;
import edu.stanford.cs.java2js.JSImage;
import edu.stanford.cs.svm.SVM;

class GImage_xrr extends GImageMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("GImage.xrr", "*");
      GImage image = (GImage)svm.pop().getValue();
      int[][] array = image.getPixelArray();
      int height = array.length;
      int width = array[0].length;
      int[][] newArray = JSImage.createPixelArray(width, height);

      for(int i = 0; i < height; ++i) {
         for(int j = 0; j < width; ++j) {
            newArray[j][height - i - 1] = array[i][j];
         }
      }

      svm.push(Value.createObject(new GImage(newArray), "GImage"));
   }
}
