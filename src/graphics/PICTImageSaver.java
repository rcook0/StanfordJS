package edu.stanford.cs.graphics;

import java.awt.Image;
import java.awt.image.ImageObserver;

class PICTImageSaver extends ImageSaver {
   private static final int OP_CLIP = 1;
   private static final int OP_VERSION = 17;
   private static final int OP_DEF_HILITE = 30;
   private static final int OP_SHORT_LINE = 34;
   private static final int OP_DIRECT_BITS_RECT = 154;
   private static final int OP_SHORT_COMMENT = 160;
   private static final int OP_LONG_COMMENT = 161;
   private static final int OP_END_PICT = 255;
   private static final int OP_HEADER = 3072;
   private static final int PS_BEGIN = 190;
   private static final int PS_END = 191;
   private static final int PS_HANDLE = 192;
   private static final int PS_DICT_SIZE = 500;
   private static final int VERSION = 767;
   private static final int PICT_PADDING = 512;
   private static final int RGB_DIRECT = 16;
   private static final int SRC_COPY = 0;
   private int[][] pixels;
   private String[] psPreview;
   private int width;
   private int height;
   private int rowBytes;
   private boolean paddingFlag = true;

   public void saveImage(Image image) {
      this.pixels = GImageTools.getPixelArray(image);
      Object property = image.getProperty("PSPreview", (ImageObserver)null);
      this.psPreview = property instanceof String[] ? (String[])property : null;
      this.width = this.pixels[0].length;
      this.height = this.pixels.length;
      this.rowBytes = 4 * this.width;
      if (this.paddingFlag) {
         this.dumpPadding();
      }

      this.dumpHeader();
      this.dumpDefHilite();
      this.dumpClipRegion();
      this.dumpBoundsMarkers();
      if (this.psPreview != null) {
         this.dumpShort(160);
         this.dumpShort(190);
      }

      this.dumpDirectBitsRect();
      if (this.psPreview != null) {
         this.dumpPSPreview();
         this.dumpShort(160);
         this.dumpShort(191);
      }

      this.dumpEndPict();
   }

   public void setPaddingFlag(boolean flag) {
      this.paddingFlag = flag;
   }

   private void dumpPadding() {
      for(int i = 0; i < 512; ++i) {
         this.dumpByte(0);
      }

   }

   private void dumpHeader() {
      this.dumpShort(0);
      this.dumpShort(0);
      this.dumpShort(0);
      this.dumpShort(this.height);
      this.dumpShort(this.width);
      this.dumpShort(17);
      this.dumpShort(767);
      this.dumpShort(3072);
      this.dumpShort(65534);
      this.dumpShort(0);
      this.dumpShort(72);
      this.dumpShort(0);
      this.dumpShort(72);
      this.dumpShort(0);
      this.dumpShort(0);
      this.dumpShort(0);
      this.dumpShort(this.height);
      this.dumpShort(this.width);
      this.dumpInt(0);
   }

   private void dumpDefHilite() {
      this.dumpShort(30);
   }

   private void dumpClipRegion() {
      this.dumpShort(1);
      this.dumpShort(10);
      this.dumpShort(0);
      this.dumpShort(0);
      this.dumpShort(this.height);
      this.dumpShort(this.width);
   }

   private void dumpBoundsMarkers() {
      this.dumpShort(34);
      this.dumpShort(0);
      this.dumpShort(0);
      this.dumpShort(0);
      this.dumpShort(34);
      this.dumpShort(this.height);
      this.dumpShort(this.width);
      this.dumpShort(0);
   }

   private void dumpDirectBitsRect() {
      this.dumpShort(154);
      this.dumpPixMap();
      this.dumpShort(0);
      this.dumpShort(0);
      this.dumpShort(this.height);
      this.dumpShort(this.width);
      this.dumpShort(0);
      this.dumpShort(0);
      this.dumpShort(this.height);
      this.dumpShort(this.width);
      this.dumpShort(0);
      this.dumpPixelData();
   }

   private void dumpPixMap() {
      this.dumpInt(255);
      this.dumpShort(this.rowBytes | '耀');
      this.dumpShort(0);
      this.dumpShort(0);
      this.dumpShort(this.height);
      this.dumpShort(this.width);
      this.dumpShort(0);
      this.dumpShort(4);
      this.dumpInt(0);
      this.dumpShort(72);
      this.dumpShort(0);
      this.dumpShort(72);
      this.dumpShort(0);
      this.dumpShort(16);
      this.dumpShort(32);
      this.dumpShort(3);
      this.dumpShort(8);
      this.dumpInt(0);
      this.dumpInt(0);
      this.dumpInt(0);
   }

   private void dumpEndPict() {
      this.dumpShort(255);
   }

   private void dumpPixelData() {
      int byteCount = 0;
      byte[] data = new byte[this.rowBytes];

      for(int i = 0; i < this.height; ++i) {
         int nBytes = this.packScanLine(data, this.pixels[i]);
         if (this.rowBytes > 250) {
            this.dumpShort(nBytes);
            byteCount += 2;
         } else {
            this.dumpByte(nBytes);
            ++byteCount;
         }

         for(int j = 0; j < nBytes; ++j) {
            this.dumpByte(data[j]);
         }

         byteCount += nBytes;
      }

      if (byteCount % 2 == 1) {
         this.dumpByte(0);
      }

   }

   private int packScanLine(byte[] data, int[] scanline) {
      int flagIndex = 0;

      for(int rgbIndex = 0; rgbIndex < 3; ++rgbIndex) {
         char rgb = "RGB".charAt(rgbIndex);
         int baseIndex = flagIndex;
         int dataIndex = flagIndex + 1;

         int count;
         int i;
         for(int scanIndex = 0; scanIndex < this.width; flagIndex = dataIndex++) {
            count = this.getPixelComponent(scanline[scanIndex++], rgb);
            data[dataIndex++] = (byte)count;
            i = 1;
            boolean matching = false;
            if (scanIndex < this.width) {
               int b1 = this.getPixelComponent(scanline[scanIndex], rgb);
               matching = count == b1;
               if (matching) {
                  while(i < 128 && scanIndex < this.width) {
                     b1 = this.getPixelComponent(scanline[scanIndex], rgb);
                     if (count != b1) {
                        break;
                     }

                     ++i;
                     ++scanIndex;
                  }
               } else {
                  while(i < 128 && scanIndex < this.width) {
                     b1 = this.getPixelComponent(scanline[scanIndex], rgb);
                     if (count == b1) {
                        --dataIndex;
                        --i;
                        --scanIndex;
                        break;
                     }

                     data[dataIndex++] = (byte)b1;
                     count = b1;
                     ++i;
                     ++scanIndex;
                  }
               }
            }

            if (matching) {
               data[flagIndex] = (byte)(128 | 129 - i);
            } else {
               data[flagIndex] = (byte)(i - 1);
            }
         }

         if (flagIndex - baseIndex > this.width + this.width / 128) {
            count = this.width;
            flagIndex = baseIndex;

            for(i = 0; i < this.width; ++i) {
               if (i % 128 == 0) {
                  int nBytes = count > 128 ? 128 : count;
                  data[flagIndex++] = (byte)(nBytes - 1);
                  count -= nBytes;
               }

               data[flagIndex++] = (byte)this.getPixelComponent(scanline[i], rgb);
            }
         }
      }

      return flagIndex;
   }

   private void dumpPSPreview() {
      this.addPSComment("/dictCount countdictstack def");
      this.addPSComment("/opCount count 1 sub def");
      this.addPSComment("500 dict begin");
      this.addPSComment("/showpage {} def");
      this.addPSComment("0 setgray 0 setlinecap");
      this.addPSComment("1 setlinewidth 0 setlinejoin");
      this.addPSComment("10 setmiterlimit [] 0 setdash");
      this.addPSComment("/languagelevel where {");
      this.addPSComment("  pop languagelevel");
      this.addPSComment("  1 ne { false setstrokeadjust false setoverprint } if");
      this.addPSComment("} if");
      this.addPSComment("gsave");
      this.addPSComment("clippath pathbbox");
      this.addPSComment("pop pop " + this.height + " add translate");
      this.addPSComment("1 -1 scale");

      for(int i = 0; i < this.psPreview.length; ++i) {
         this.addPSComment(this.psPreview[i]);
      }

      this.addPSComment("grestore");
      this.addPSComment("end");
      this.addPSComment("count opCount sub {pop} repeat");
      this.addPSComment("countdictstack dictCount sub {end} repeat");
   }

   private void addPSComment(String line) {
      if (line.length() % 2 == 0) {
         line = line + " ";
      }

      this.dumpShort(161);
      this.dumpShort(192);
      this.dumpShort(line.length() + 1);

      for(int i = 0; i < line.length(); ++i) {
         this.dumpByte(line.charAt(i));
      }

      this.dumpByte(13);
   }
}
