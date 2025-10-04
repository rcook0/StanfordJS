package edu.stanford.cs.sjslib.graphics;

public class SJSGImageClass extends SJSGObjectClass {
   public SJSGImageClass() {
      this.defineMethod("new", new GImage_new());
      this.defineMethod("getPixelArray", new GImage_getPixelArray());
      this.defineMethod("getRed", new GImage_getRed());
      this.defineMethod("getGreen", new GImage_getGreen());
      this.defineMethod("getBlue", new GImage_getBlue());
      this.defineMethod("getAlpha", new GImage_getAlpha());
      this.defineMethod("createRGBPixel", new GImage_createRGBPixel());
      this.defineMethod("createPixelArray", new GImage_createPixelArray());
      this.defineMethod("xfv", new GImage_xfv());
      this.defineMethod("xfh", new GImage_xfh());
      this.defineMethod("xrl", new GImage_xrl());
      this.defineMethod("xrr", new GImage_xrr());
      this.defineMethod("xgs", new GImage_xgs());
      this.defineMethod("xgt", new GImage_xgt());
      this.defineMethod("xeq", new GImage_xeq());
      this.defineMethod("xcr", new GImage_xcr());
   }
}
