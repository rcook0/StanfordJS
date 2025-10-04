package edu.stanford.cs.graphics;

class IndexGif89Frame extends Gif89Frame {
   public IndexGif89Frame(int width, int height, byte[] ci_pixels) {
      this.theWidth = width;
      this.theHeight = height;
      this.ciPixels = new byte[this.theWidth * this.theHeight];
      System.arraycopy(ci_pixels, 0, this.ciPixels, 0, this.ciPixels.length);
   }

   Object getPixelSource() {
      return this.ciPixels;
   }
}
