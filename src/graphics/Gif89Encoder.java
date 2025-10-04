package edu.stanford.cs.graphics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Vector;

class Gif89Encoder {
   private Dimension dispDim;
   private GifColorTable colorTable;
   private int bgIndex;
   private int loopCount;
   private String theComments;
   private Vector<Gif89Frame> vFrames;

   public Gif89Encoder() {
      this.dispDim = new Dimension(0, 0);
      this.bgIndex = 0;
      this.loopCount = 1;
      this.vFrames = new Vector();
      this.colorTable = new GifColorTable();
   }

   public Gif89Encoder(Image static_image) throws IOException {
      this();
      this.addFrame(static_image);
   }

   public Gif89Encoder(Color[] colors) {
      this.dispDim = new Dimension(0, 0);
      this.bgIndex = 0;
      this.loopCount = 1;
      this.vFrames = new Vector();
      this.colorTable = new GifColorTable(colors);
   }

   public Gif89Encoder(Color[] colors, int width, int height, byte[] ci_pixels) throws IOException {
      this(colors);
      this.addFrame(width, height, ci_pixels);
   }

   public int getFrameCount() {
      return this.vFrames.size();
   }

   public Gif89Frame getFrameAt(int index) {
      return this.isOk(index) ? (Gif89Frame)this.vFrames.elementAt(index) : null;
   }

   public void addFrame(Gif89Frame gf) throws IOException {
      this.accommodateFrame(gf);
      this.vFrames.addElement(gf);
   }

   public void addFrame(Image image) throws IOException {
      this.addFrame((Gif89Frame)(new DirectGif89Frame(image)));
   }

   public void addFrame(int width, int height, byte[] ci_pixels) throws IOException {
      this.addFrame((Gif89Frame)(new IndexGif89Frame(width, height, ci_pixels)));
   }

   public void insertFrame(int index, Gif89Frame gf) throws IOException {
      this.accommodateFrame(gf);
      this.vFrames.insertElementAt(gf, index);
   }

   public void setTransparentIndex(int index) {
      this.colorTable.setTransparent(index);
   }

   public void setLogicalDisplay(Dimension dim, int background) {
      this.dispDim = new Dimension(dim);
      this.bgIndex = background;
   }

   public void setLoopCount(int count) {
      this.loopCount = count;
   }

   public void setComments(String comments) {
      this.theComments = comments;
   }

   public void setUniformDelay(int interval) {
      for(int i = 0; i < this.vFrames.size(); ++i) {
         ((Gif89Frame)this.vFrames.elementAt(i)).setDelay(interval);
      }

   }

   public void encode(OutputStream out) throws IOException {
      int nframes = this.getFrameCount();
      boolean is_sequence = nframes > 1;
      this.colorTable.closePixelProcessing();
      Gif89Put.ascii("GIF89a", out);
      this.writeLogicalScreenDescriptor(out);
      this.colorTable.encode(out);
      if (is_sequence && this.loopCount != 1) {
         this.writeNetscapeExtension(out);
      }

      if (this.theComments != null && this.theComments.length() > 0) {
         this.writeCommentExtension(out);
      }

      for(int i = 0; i < nframes; ++i) {
         ((Gif89Frame)this.vFrames.elementAt(i)).encode(out, is_sequence, this.colorTable.getDepth(), this.colorTable.getTransparent());
      }

      out.write(59);
      out.flush();
   }

   private void accommodateFrame(Gif89Frame gf) throws IOException {
      this.dispDim.width = Math.max(this.dispDim.width, gf.getWidth());
      this.dispDim.height = Math.max(this.dispDim.height, gf.getHeight());
      this.colorTable.processPixels(gf);
   }

   private void writeLogicalScreenDescriptor(OutputStream os) throws IOException {
      Gif89Put.leShort(this.dispDim.width, os);
      Gif89Put.leShort(this.dispDim.height, os);
      os.write(240 | this.colorTable.getDepth() - 1);
      os.write(this.bgIndex);
      os.write(0);
   }

   private void writeNetscapeExtension(OutputStream os) throws IOException {
      os.write(33);
      os.write(255);
      os.write(11);
      Gif89Put.ascii("NETSCAPE2.0", os);
      os.write(3);
      os.write(1);
      Gif89Put.leShort(this.loopCount > 1 ? this.loopCount - 1 : 0, os);
      os.write(0);
   }

   private void writeCommentExtension(OutputStream os) throws IOException {
      os.write(33);
      os.write(254);
      int remainder = this.theComments.length() % 255;
      int nsubblocks_full = this.theComments.length() / 255;
      int nsubblocks = nsubblocks_full + (remainder > 0 ? 1 : 0);
      int ibyte = 0;

      for(int isb = 0; isb < nsubblocks; ++isb) {
         int size = isb < nsubblocks_full ? 255 : remainder;
         os.write(size);
         Gif89Put.ascii(this.theComments.substring(ibyte, ibyte + size), os);
         ibyte += size;
      }

      os.write(0);
   }

   private boolean isOk(int frame_index) {
      return frame_index >= 0 && frame_index < this.vFrames.size();
   }
}
