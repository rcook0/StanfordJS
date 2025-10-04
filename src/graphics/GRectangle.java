package edu.stanford.cs.graphics;

import java.awt.Rectangle;

public class GRectangle {
   private double x;
   private double y;
   private double width;
   private double height;

   public GRectangle() {
      this(0.0D, 0.0D, 0.0D, 0.0D);
   }

   public GRectangle(double x, double y, double width, double height) {
      this.x = x;
      this.y = y;
      this.width = width;
      this.height = height;
   }

   public GRectangle(double width, double height) {
      this(0.0D, 0.0D, width, height);
   }

   public GRectangle(GPoint pt, GDimension size) {
      this(pt.getX(), pt.getY(), size.getWidth(), size.getHeight());
   }

   public GRectangle(GPoint pt) {
      this(pt.getX(), pt.getY(), 0.0D, 0.0D);
   }

   public GRectangle(GDimension size) {
      this(0.0D, 0.0D, size.getWidth(), size.getHeight());
   }

   public GRectangle(GRectangle r) {
      this(r.x, r.y, r.width, r.height);
   }

   public double getX() {
      return this.x;
   }

   public double getY() {
      return this.y;
   }

   public double getWidth() {
      return this.width;
   }

   public double getHeight() {
      return this.height;
   }

   public void setBounds(double x, double y, double width, double height) {
      this.x = x;
      this.y = y;
      this.width = width;
      this.height = height;
   }

   public final void setBounds(GPoint pt, GDimension size) {
      this.setBounds(pt.getX(), pt.getY(), size.getWidth(), size.getHeight());
   }

   public final void setBounds(GRectangle bounds) {
      this.setBounds(bounds.x, bounds.y, bounds.width, bounds.height);
   }

   public GRectangle getBounds() {
      return new GRectangle(this);
   }

   public void setLocation(double x, double y) {
      this.x = x;
      this.y = y;
   }

   public void setLocation(GPoint pt) {
      this.setLocation(pt.getX(), pt.getY());
   }

   public GPoint getLocation() {
      return new GPoint(this.x, this.y);
   }

   public void translate(double dx, double dy) {
      this.x += dx;
      this.y += dy;
   }

   public void setSize(double width, double height) {
      this.width = width;
      this.height = height;
   }

   public void setSize(GDimension size) {
      this.setSize(size.getWidth(), size.getHeight());
   }

   public GDimension getSize() {
      return new GDimension(this.width, this.height);
   }

   public void grow(double dx, double dy) {
      this.x -= dx;
      this.y -= dy;
      this.width += 2.0D * dx;
      this.height += 2.0D * dy;
   }

   public boolean isEmpty() {
      return this.width <= 0.0D || this.height <= 0.0D;
   }

   public boolean contains(double x, double y) {
      return x >= x && y >= y && x < x + this.width && y < y + this.height;
   }

   public boolean contains(GPoint pt) {
      return this.contains(pt.getX(), pt.getY());
   }

   public boolean intersects(GRectangle r2) {
      if (this.x > r2.x + r2.width) {
         return false;
      } else if (this.y > r2.y + r2.height) {
         return false;
      } else if (r2.x > this.x + this.width) {
         return false;
      } else {
         return !(r2.y > this.y + this.height);
      }
   }

   public GRectangle intersection(GRectangle r2) {
      double x1 = Math.max(this.x, r2.x);
      double y1 = Math.max(this.y, r2.y);
      double x2 = Math.min(this.x + this.width, r2.x + r2.width);
      double y2 = Math.min(this.y + this.height, r2.y + r2.height);
      return new GRectangle(x1, y1, x2 - x1, y2 - y1);
   }

   public GRectangle union(GRectangle r2) {
      if (this.isEmpty()) {
         return new GRectangle(r2);
      } else if (r2.isEmpty()) {
         return new GRectangle(this);
      } else {
         double x1 = Math.min(this.x, r2.x);
         double y1 = Math.min(this.y, r2.y);
         double x2 = Math.max(this.x + this.width, r2.x + r2.width);
         double y2 = Math.max(this.y + this.height, r2.y + r2.height);
         return new GRectangle(x1, y1, x2 - x1, y2 - y1);
      }
   }

   public void add(GRectangle r) {
      if (!r.isEmpty()) {
         double x2 = Math.max(this.x + this.width, r.x + r.width);
         double y2 = Math.max(this.y + this.height, r.y + r.height);
         this.x = Math.min(r.x, this.x);
         this.y = Math.min(r.y, this.y);
         this.width = x2 - this.x;
         this.height = y2 - this.y;
      }
   }

   public void add(double x, double y) {
      if (x < this.x) {
         this.width += this.x - x;
         this.x = x;
      } else if (x > this.x + this.width) {
         this.width = x - this.x;
      }

      if (y < this.y) {
         this.height += this.y - y;
         this.y = y;
      } else if (y > this.y + this.height) {
         this.height = y - this.y;
      }

   }

   public void add(GPoint pt) {
      this.add(pt.getX(), pt.getY());
   }

   public Rectangle toRectangle() {
      return new Rectangle(GMath.round(this.x), GMath.round(this.y), GMath.round(this.width), GMath.round(this.height));
   }

   public int hashCode() {
      int hash = (new Float((float)this.x)).hashCode();
      hash = 37 * hash ^ (new Float((float)this.y)).hashCode();
      hash = 37 * hash ^ (new Float((float)this.width)).hashCode();
      hash = 37 * hash ^ (new Float((float)this.height)).hashCode();
      return hash;
   }

   public boolean equals(Object obj) {
      if (!(obj instanceof GRectangle)) {
         return false;
      } else {
         GRectangle r = (GRectangle)obj;
         if ((float)this.x != (float)r.x) {
            return false;
         } else if ((float)this.y != (float)r.y) {
            return false;
         } else if ((float)this.width != (float)r.width) {
            return false;
         } else {
            return (float)this.height == (float)r.height;
         }
      }
   }

   public String toString() {
      return "GRectangle(" + GObject.dts(this.x) + ", " + GObject.dts(this.y) + ", " + GObject.dts(this.width) + ", " + GObject.dts(this.height) + ")";
   }
}
