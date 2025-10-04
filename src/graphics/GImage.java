package edu.stanford.cs.graphics;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;

public class GImage extends GObject implements GResizable {
   private Image image;
   private double width;
   private double height;
   private boolean sizeDetermined;

   public GImage(Image image) {
      this(image, 0.0D, 0.0D);
   }

   public GImage(String name) {
      this(name, 0.0D, 0.0D);
   }

   public GImage(int[][] array) {
      this(array, 0.0D, 0.0D);
   }

   public GImage(Image image, double x, double y) {
      this.setImage(image);
      this.setLocation(x, y);
   }

   public GImage(String name, double x, double y) {
      this(GImageTools.loadImage(name), x, y);
   }

   public GImage(int[][] array, double x, double y) {
      this(GImageTools.createImage(array), x, y);
   }

   public void setImage(Image image) {
      this.image = GImageTools.loadImage(image);
      this.sizeDetermined = false;
      this.determineSize();
      this.repaint();
   }

   public void setImage(String name) {
      this.setImage(GImageTools.loadImage(name));
   }

   public Image getImage() {
      return this.image;
   }

   public void saveImage(String filename) {
      GImageTools.saveImage(this.image, filename);
   }

   public void saveImage(File file) {
      GImageTools.saveImage(this.image, file);
   }

   public final void setSize(double width, double height) {
      if (this.isTransformed()) {
         throw new RuntimeException("setSize called on transformed object");
      } else {
         this.width = width;
         this.height = height;
         this.repaint();
      }
   }

   public final void setSize(GDimension size) {
      this.setSize(size.getWidth(), size.getHeight());
   }

   public void setBounds(double x, double y, double width, double height) {
      if (this.isTransformed()) {
         throw new RuntimeException("setBounds called on transformed object");
      } else {
         this.width = width;
         this.height = height;
         this.setLocation(x, y);
      }
   }

   public final void setBounds(GRectangle bounds) {
      this.setBounds(bounds.getX(), bounds.getY(), bounds.getWidth(), bounds.getHeight());
   }

   public int[][] getPixelArray() {
      return GImageTools.getPixelArray(this.image);
   }

   public static int getAlpha(int pixel) {
      return pixel >> 24 & 255;
   }

   public static int getRed(int pixel) {
      return pixel >> 16 & 255;
   }

   public static int getGreen(int pixel) {
      return pixel >> 8 & 255;
   }

   public static int getBlue(int pixel) {
      return pixel & 255;
   }

   public static int createRGBPixel(int red, int green, int blue) {
      return createRGBPixel(red, green, blue, 255);
   }

   public static int createRGBPixel(int red, int green, int blue, int alpha) {
      return alpha << 24 | red << 16 | green << 8 | blue;
   }

   protected GRectangle localBounds(GTransform ctm) {
      this.determineSize();
      GRectangle bb = new GRectangle(ctm.transform(0.0D, 0.0D));
      bb.add(ctm.transform(this.width, 0.0D));
      bb.add(ctm.transform(0.0D, this.height));
      bb.add(ctm.transform(this.width, this.height));
      return bb;
   }

   protected boolean localContains(double x, double y) {
      this.determineSize();
      return x >= 0.0D && x < this.width && y >= 0.0D && y < this.height;
   }

   protected void paint2d(Graphics2D g) {
      Component imageObserver = this.getComponent();
      if (imageObserver == null) {
         imageObserver = GImageTools.getImageObserver();
      }

      if (this.image != null && imageObserver != null) {
         this.determineSize();
         Color color = this.getObjectColor();
         if (color == null) {
            g.drawImage(this.image, 0, 0, GMath.round(this.width), GMath.round(this.height), imageObserver);
         } else {
            g.drawImage(this.image, 0, 0, GMath.round(this.width), GMath.round(this.height), color, imageObserver);
         }
      }

   }

   private void determineSize() {
      if (!this.sizeDetermined) {
         Component component = this.getComponent();
         if (component == null) {
            component = GImageTools.getImageObserver();
         }

         this.width = (double)this.image.getWidth(component);
         this.height = (double)this.image.getHeight(component);
         this.sizeDetermined = true;
      }
   }
}
