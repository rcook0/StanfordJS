package edu.stanford.cs.graphics;

import java.awt.Dimension;

public class GDimension {
   private double width;
   private double height;

   public GDimension() {
      this(0.0D, 0.0D);
   }

   public GDimension(double width, double height) {
      this.width = width;
      this.height = height;
   }

   public GDimension(GDimension size) {
      this(size.width, size.height);
   }

   public GDimension(Dimension size) {
      this((double)size.width, (double)size.height);
   }

   public double getWidth() {
      return this.width;
   }

   public double getHeight() {
      return this.height;
   }

   public void setSize(double width, double height) {
      this.width = width;
      this.height = height;
   }

   public void setSize(GDimension size) {
      this.setSize(size.width, size.height);
   }

   public GDimension getSize() {
      return new GDimension(this.width, this.height);
   }

   public Dimension toDimension() {
      return new Dimension(GMath.round(this.width), GMath.round(this.height));
   }

   public String toString() {
      return "GDimension(" + GObject.dts(this.width) + ", " + GObject.dts(this.height) + ")";
   }

   public int hashCode() {
      return (new Float((float)this.width)).hashCode() ^ 37 * (new Float((float)this.height)).hashCode();
   }

   public boolean equals(Object obj) {
      if (!(obj instanceof GDimension)) {
         return false;
      } else {
         GDimension dim = (GDimension)obj;
         return (float)this.width == (float)dim.width && (float)this.height == (float)dim.height;
      }
   }
}
