package edu.stanford.cs.graphics;

import java.awt.Image;

class TIFFImageSaver extends ImageSaver {
   private static final int HEADER_SIZE = 8;
   private static final int IFD_OP_COUNT = 13;
   public static final int TT_BYTE = 1;
   public static final int TT_ASCII = 2;
   public static final int TT_SHORT = 3;
   public static final int TT_LONG = 4;
   public static final int TT_RATIONAL = 5;
   private int[][] pixels;
   private int width;
   private int height;
   private int offsetIFD;
   private int offsetNullIFD;
   private int offsetBitsPerSample;
   private int offsetXResolution;
   private int offsetYResolution;
   private int offsetStripPointers;
   private int offsetStripByteCounts;
   private int offsetData;
   private int stripDelta;

   public void saveImage(Image image) {
      this.pixels = GImageTools.getPixelArray(image);
      this.width = this.pixels[0].length;
      this.height = this.pixels.length;
      this.calculateOffsets();
      this.dumpHeader();
      this.dumpIFD();
      this.dumpNullIFD();
      this.dumpBitsPerSampleData();
      this.dumpResolutionData();
      this.dumpStripPointers();
      this.dumpStripByteCounts();
      this.dumpStripData();
   }

   private void dumpHeader() {
      this.dumpByte(77);
      this.dumpByte(77);
      this.dumpShort(42);
      this.dumpInt(8);
   }

   private void dumpIFD() {
      this.dumpShort(13);
      this.dumpIFDNewSubFileType();
      this.dumpIFDImageWidth();
      this.dumpIFDImageHeight();
      this.dumpIFDBitsPerSample();
      this.dumpIFDCompression();
      this.dumpIFDPhotometricInterpration();
      this.dumpIFDStripPointers();
      this.dumpIFDSamplesPerPixel();
      this.dumpIFDRowsPerStrip();
      this.dumpIFDStripByteCounts();
      this.dumpIFDXResolution();
      this.dumpIFDYResolution();
      this.dumpIFDResolutionUnit();
   }

   private void dumpIFDNewSubFileType() {
      this.dumpShort(254);
      this.dumpShort(4);
      this.dumpInt(1);
      this.dumpInt(0);
   }

   private void dumpIFDImageWidth() {
      this.dumpShort(256);
      this.dumpShort(4);
      this.dumpInt(1);
      this.dumpInt(this.width);
   }

   private void dumpIFDImageHeight() {
      this.dumpShort(257);
      this.dumpShort(4);
      this.dumpInt(1);
      this.dumpInt(this.height);
   }

   private void dumpIFDBitsPerSample() {
      this.dumpShort(258);
      this.dumpShort(4);
      this.dumpInt(3);
      this.dumpInt(this.offsetBitsPerSample);
   }

   private void dumpIFDCompression() {
      this.dumpShort(259);
      this.dumpShort(3);
      this.dumpInt(1);
      this.dumpShort(1);
      this.dumpShort(0);
   }

   private void dumpIFDPhotometricInterpration() {
      this.dumpShort(262);
      this.dumpShort(3);
      this.dumpInt(1);
      this.dumpShort(2);
      this.dumpShort(0);
   }

   private void dumpIFDStripPointers() {
      this.dumpShort(273);
      this.dumpShort(4);
      this.dumpInt(this.height);
      this.dumpInt(this.offsetStripPointers);
   }

   private void dumpIFDSamplesPerPixel() {
      this.dumpShort(277);
      this.dumpShort(3);
      this.dumpInt(1);
      this.dumpShort(3);
      this.dumpShort(0);
   }

   private void dumpIFDRowsPerStrip() {
      this.dumpShort(278);
      this.dumpShort(4);
      this.dumpInt(1);
      this.dumpInt(1);
   }

   private void dumpIFDStripByteCounts() {
      this.dumpShort(279);
      this.dumpShort(4);
      this.dumpInt(this.height);
      this.dumpInt(this.offsetStripByteCounts);
   }

   private void dumpIFDXResolution() {
      this.dumpShort(282);
      this.dumpShort(5);
      this.dumpInt(1);
      this.dumpInt(this.offsetXResolution);
   }

   private void dumpIFDYResolution() {
      this.dumpShort(283);
      this.dumpShort(5);
      this.dumpInt(1);
      this.dumpInt(this.offsetYResolution);
   }

   private void dumpIFDResolutionUnit() {
      this.dumpShort(296);
      this.dumpShort(3);
      this.dumpInt(1);
      this.dumpShort(1);
      this.dumpShort(0);
   }

   private void dumpNullIFD() {
      this.dumpInt(0);
   }

   private void dumpBitsPerSampleData() {
      this.dumpInt(8);
      this.dumpInt(8);
      this.dumpInt(8);
   }

   private void dumpResolutionData() {
      this.dumpInt(72);
      this.dumpInt(1);
      this.dumpInt(72);
      this.dumpInt(1);
   }

   private void dumpStripPointers() {
      for(int i = 0; i < this.height; ++i) {
         this.dumpInt(this.offsetData + i * this.stripDelta);
      }

   }

   private void dumpStripByteCounts() {
      for(int i = 0; i < this.height; ++i) {
         this.dumpInt(3 * this.width);
      }

   }

   private void dumpStripData() {
      for(int i = 0; i < this.height; ++i) {
         int k;
         for(k = 0; k < this.width; ++k) {
            int pixel = this.pixels[i][k];
            this.dumpByte(this.getPixelComponent(pixel, 'R'));
            this.dumpByte(this.getPixelComponent(pixel, 'G'));
            this.dumpByte(this.getPixelComponent(pixel, 'B'));
         }

         for(k = 3 * this.width; k < this.stripDelta; ++k) {
            this.dumpByte(0);
         }
      }

   }

   private void calculateOffsets() {
      this.offsetIFD = 8;
      this.offsetNullIFD = this.offsetIFD + 156 + 2;
      this.offsetBitsPerSample = this.offsetNullIFD + 4;
      this.offsetXResolution = this.offsetBitsPerSample + 12;
      this.offsetYResolution = this.offsetXResolution + 8;
      this.offsetStripPointers = this.offsetYResolution + 8;
      this.offsetStripByteCounts = this.offsetStripPointers + 4 * this.height;
      this.offsetData = this.offsetStripByteCounts + 4 * this.height;
      this.stripDelta = 3 * this.width + 3 & -4;
   }
}
