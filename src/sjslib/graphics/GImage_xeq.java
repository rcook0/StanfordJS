package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.graphics.GImage;
import edu.stanford.cs.svm.SVM;

class GImage_xeq extends GImageMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("GImage.xeq", "*");
      GImage image = (GImage)svm.pop().getValue();
      int[][] array = image.getPixelArray();
      int height = array.length;
      int width = array[0].length;
      int n = width * height;
      int[] histogram = new int[256];

      int total;
      int i;
      int j;
      for(total = 0; total < height; ++total) {
         for(i = 0; i < width; ++i) {
            j = this.computeLuminosity(array[total][i]);
            int var10002 = histogram[j]++;
         }
      }

      total = 0;

      for(i = 0; i < 256; ++i) {
         total += histogram[i];
         histogram[i] = total;
      }

      for(i = 0; i < height; ++i) {
         for(j = 0; j < width; ++j) {
            int xx = this.computeLuminosity(array[i][j]);
            double fraction = (double)histogram[xx] / (double)n;
            xx = (int)(255.0D * fraction + 0.5D);
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
