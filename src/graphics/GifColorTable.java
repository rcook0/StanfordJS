package edu.stanford.cs.graphics;

import java.awt.Color;
import java.io.IOException;
import java.io.OutputStream;

class GifColorTable {
   private int[] theColors = new int[256];
   private int colorDepth;
   private int transparentIndex = -1;
   private int ciCount = 0;
   private ReverseColorMap ciLookup;

   GifColorTable() {
      this.ciLookup = new ReverseColorMap();
   }

   GifColorTable(Color[] colors) {
      int n2copy = Math.min(this.theColors.length, colors.length);

      for(int i = 0; i < n2copy; ++i) {
         this.theColors[i] = colors[i].getRGB();
      }

   }

   int getDepth() {
      return this.colorDepth;
   }

   int getTransparent() {
      return this.transparentIndex;
   }

   void setTransparent(int color_index) {
      this.transparentIndex = color_index;
   }

   void processPixels(Gif89Frame gf) throws IOException {
      if (gf instanceof DirectGif89Frame) {
         this.filterPixels((DirectGif89Frame)gf);
      } else {
         this.trackPixelUsage((IndexGif89Frame)gf);
      }

   }

   void closePixelProcessing() {
      this.colorDepth = this.computeColorDepth(this.ciCount);
   }

   void encode(OutputStream os) throws IOException {
      int palette_size = 1 << this.colorDepth;

      for(int i = 0; i < palette_size; ++i) {
         os.write(this.theColors[i] >> 16 & 255);
         os.write(this.theColors[i] >> 8 & 255);
         os.write(this.theColors[i] & 255);
      }

   }

   private void filterPixels(DirectGif89Frame dgf) throws IOException {
      if (this.ciLookup == null) {
         throw new IOException("RGB frames require palette autodetection");
      } else {
         int[] argb_pixels = (int[])dgf.getPixelSource();
         byte[] ci_pixels = dgf.getPixelSink();
         int npixels = argb_pixels.length;

         for(int i = 0; i < npixels; ++i) {
            int argb = argb_pixels[i];
            if (argb >>> 24 < 128) {
               if (this.transparentIndex == -1) {
                  this.transparentIndex = this.ciCount;
               } else if (argb != this.theColors[this.transparentIndex]) {
                  ci_pixels[i] = (byte)this.transparentIndex;
                  continue;
               }
            }

            int color_index = this.ciLookup.getPaletteIndex(argb & 16777215);
            if (color_index == -1) {
               if (this.ciCount == 256) {
                  throw new IOException("can't encode as GIF (> 256 colors)");
               }

               this.theColors[this.ciCount] = argb;
               this.ciLookup.put(argb & 16777215, this.ciCount);
               ci_pixels[i] = (byte)this.ciCount;
               ++this.ciCount;
            } else {
               ci_pixels[i] = (byte)color_index;
            }
         }

      }
   }

   private void trackPixelUsage(IndexGif89Frame igf) {
      byte[] ci_pixels = (byte[])igf.getPixelSource();
      int npixels = ci_pixels.length;

      for(int i = 0; i < npixels; ++i) {
         if (ci_pixels[i] >= this.ciCount) {
            this.ciCount = ci_pixels[i] + 1;
         }
      }

   }

   private int computeColorDepth(int colorcount) {
      if (colorcount <= 2) {
         return 1;
      } else if (colorcount <= 4) {
         return 2;
      } else {
         return colorcount <= 16 ? 4 : 8;
      }
   }
}
