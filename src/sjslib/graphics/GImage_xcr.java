package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.graphics.GImage;
import edu.stanford.cs.java2js.JSImage;
import edu.stanford.cs.svm.SVM;

class GImage_xcr extends GImageMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("GImage.xcr", "*IIII");
      int width = svm.popInteger();
      int height = svm.popInteger();
      int y = svm.popInteger();
      int x = svm.popInteger();
      GImage image = (GImage)svm.pop().getValue();
      int[][] array = image.getPixelArray();
      int[][] crop = JSImage.createPixelArray(width, height);

      for(int i = 0; i < height; ++i) {
         for(int j = 0; j < width; ++j) {
            crop[i][j] = array[y + i][x + j];
         }
      }

      svm.push(Value.createObject(new GImage(crop), "GImage"));
   }
}
