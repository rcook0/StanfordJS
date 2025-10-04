package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.graphics.GImage;
import edu.stanford.cs.java2js.JSImage;
import edu.stanford.cs.svm.SVM;

class GImage_xrl extends GImageMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("GImage.xrl", "*");
      GImage image = (GImage)svm.pop().getValue();
      int[][] array = image.getPixelArray();
      int height = array.length;
      int width = array[0].length;
      int[][] newArray = JSImage.createPixelArray(width, height);

      for(int i = 0; i < height; ++i) {
         for(int j = 0; j < width; ++j) {
            newArray[width - j - 1][i] = array[i][j];
         }
      }

      svm.push(Value.createObject(new GImage(newArray), "GImage"));
   }
}
