package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.graphics.GImage;
import edu.stanford.cs.svm.SVM;

class GImage_xgs extends GImageMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("GImage.xgs", "*");
      GImage image = (GImage)svm.pop().getValue();
      int[][] array = image.getPixelArray();
      int height = array.length;
      int width = array[0].length;

      for(int i = 0; i < height; ++i) {
         for(int j = 0; j < width; ++j) {
            int xx = this.computeLuminosity(array[i][j]);
            array[i][j] = GImage.createRGBPixel(xx, xx, xx);
         }
      }

      svm.push(Value.createObject(new GImage(array), "GImage"));
   }

   private int computeLuminosity(int pixel) {
      int r = GImage.getRed(pixel);
      int g = GImage.getGreen(pixel);
      int b = GImage.getBlue(pixel);
      return (int)Math.floor(0.299D * (double)r + 0.587D * (double)g + 0.114D * (double)b + 0.5D);
   }
}
