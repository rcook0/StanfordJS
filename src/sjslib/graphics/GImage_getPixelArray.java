package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;
import edu.stanford.cs.svm.SVMArray;

class GImage_getPixelArray extends GImageMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("GImage.getPixelArray", "");
      int[][] pixels = this.getGImage(svm, receiver).getPixelArray();
      int height = pixels.length;
      int width = pixels[0].length;
      SVMArray array = new SVMArray();

      for(int i = 0; i < height; ++i) {
         SVMArray row = new SVMArray();

         for(int j = 0; j < width; ++j) {
            row.add(Value.createInteger(pixels[i][j]));
         }

         array.add(Value.createObject(row, "Array"));
      }

      svm.push(Value.createObject(array, "Array"));
   }
}
