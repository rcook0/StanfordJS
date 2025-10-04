package edu.stanford.cs.graphics;

import java.awt.Image;
import java.awt.image.PixelGrabber;
import java.io.IOException;

class DirectGif89Frame extends Gif89Frame {
   private int[] argbPixels;

   public DirectGif89Frame(Image img) throws IOException {
      PixelGrabber pg = new PixelGrabber(img, 0, 0, -1, -1, true);
      String errmsg = null;

      try {
         if (!pg.grabPixels()) {
            errmsg = "can't grab pixels from image";
         }
      } catch (InterruptedException var5) {
         errmsg = "interrupted grabbing pixels from image";
      }

      if (errmsg != null) {
         throw new IOException(errmsg + " (" + this.getClass().getName() + ")");
      } else {
         this.theWidth = pg.getWidth();
         this.theHeight = pg.getHeight();
         this.argbPixels = (int[])pg.getPixels();
         this.ciPixels = new byte[this.argbPixels.length];
      }
   }

   public DirectGif89Frame(int width, int height, int[] argb_pixels) {
      this.theWidth = width;
      this.theHeight = height;
      this.argbPixels = new int[this.theWidth * this.theHeight];
      System.arraycopy(argb_pixels, 0, this.argbPixels, 0, this.argbPixels.length);
      this.ciPixels = new byte[this.argbPixels.length];
   }

   Object getPixelSource() {
      return this.argbPixels;
   }
}
