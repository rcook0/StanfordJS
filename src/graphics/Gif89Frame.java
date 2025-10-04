package edu.stanford.cs.graphics;

import java.awt.Point;
import java.io.IOException;
import java.io.OutputStream;

abstract class Gif89Frame {
   public static final int DM_UNDEFINED = 0;
   public static final int DM_LEAVE = 1;
   public static final int DM_BGCOLOR = 2;
   public static final int DM_REVERT = 3;
   int theWidth = -1;
   int theHeight = -1;
   byte[] ciPixels;
   private Point thePosition = new Point(0, 0);
   private boolean isInterlaced;
   private int csecsDelay;
   private int disposalCode = 1;

   public void setPosition(Point p) {
      this.thePosition = new Point(p);
   }

   public void setInterlaced(boolean b) {
      this.isInterlaced = b;
   }

   public void setDelay(int interval) {
      this.csecsDelay = interval;
   }

   public void setDisposalMode(int code) {
      this.disposalCode = code;
   }

   abstract Object getPixelSource();

   int getWidth() {
      return this.theWidth;
   }

   int getHeight() {
      return this.theHeight;
   }

   byte[] getPixelSink() {
      return this.ciPixels;
   }

   void encode(OutputStream os, boolean epluribus, int color_depth, int transparent_index) throws IOException {
      this.writeGraphicControlExtension(os, epluribus, transparent_index);
      this.writeImageDescriptor(os);
      (new GifPixelsEncoder(this.theWidth, this.theHeight, this.ciPixels, this.isInterlaced, color_depth)).encode(os);
   }

   private void writeGraphicControlExtension(OutputStream os, boolean epluribus, int itransparent) throws IOException {
      int transflag = itransparent == -1 ? 0 : 1;
      if (transflag == 1 || epluribus) {
         os.write(33);
         os.write(249);
         os.write(4);
         os.write(this.disposalCode << 2 | transflag);
         Gif89Put.leShort(this.csecsDelay, os);
         os.write(itransparent);
         os.write(0);
      }

   }

   private void writeImageDescriptor(OutputStream os) throws IOException {
      os.write(44);
      Gif89Put.leShort(this.thePosition.x, os);
      Gif89Put.leShort(this.thePosition.y, os);
      Gif89Put.leShort(this.theWidth, os);
      Gif89Put.leShort(this.theHeight, os);
      os.write(this.isInterlaced ? 64 : 0);
   }
}
