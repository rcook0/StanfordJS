package edu.stanford.cs.graphics;

import java.awt.Point;

public class GPoint {
   private double x;
   private double y;

   public GPoint() {
      this(0.0D, 0.0D);
   }

   public GPoint(double x, double y) {
      this.x = x;
      this.y = y;
   }

   public GPoint(GPoint p) {
      this(p.x, p.y);
   }

   public GPoint(Point p) {
      this((double)p.x, (double)p.y);
   }

   public double getX() {
      return this.x;
   }

   public double getY() {
      return this.y;
   }

   public void setLocation(double x, double y) {
      this.x = x;
      this.y = y;
   }

   public void setLocation(GPoint p) {
      this.setLocation(p.x, p.y);
   }

   public GPoint getLocation() {
      return new GPoint(this.x, this.y);
   }

   public void translate(double dx, double dy) {
      this.x += dx;
      this.y += dy;
   }

   public Point toPoint() {
      return new Point(GMath.round(this.x), GMath.round(this.y));
   }

   public String toString() {
      return "GPoint(" + GObject.dts(this.x) + ", " + GObject.dts(this.y) + ")";
   }

   public int hashCode() {
      return (new Float((float)this.x)).hashCode() ^ 37 * (new Float((float)this.y)).hashCode();
   }

   public boolean equals(Object obj) {
      if (!(obj instanceof GPoint)) {
         return false;
      } else {
         GPoint pt = (GPoint)obj;
         return (float)this.x == (float)pt.x && (float)this.y == (float)pt.y;
      }
   }
}
