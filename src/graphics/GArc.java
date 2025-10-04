package edu.stanford.cs.graphics;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Arc2D;
import java.awt.geom.Arc2D.Double;

public class GArc extends GObject implements GFillable {
   public static final double ARC_TOLERANCE = 2.5D;
   private double width;
   private double height;
   private double start;
   private double sweep;
   private Color fillColor;
   private boolean isFilled;

   public GArc(double width, double height, double start, double sweep) {
      this(0.0D, 0.0D, width, height, start, sweep);
   }

   public GArc(double x, double y, double width, double height, double start, double sweep) {
      this.width = width;
      this.height = height;
      this.start = start;
      this.sweep = sweep;
      this.setLocation(x, y);
   }

   public void setStartAngle(double start) {
      this.start = start;
      this.repaint();
   }

   public double getStartAngle() {
      return this.start;
   }

   public void setSweepAngle(double sweep) {
      this.sweep = sweep;
      this.repaint();
   }

   public double getSweepAngle() {
      return this.sweep;
   }

   public GPoint getStartPoint() {
      GPoint pt = this.getCTM().transform(this.getArcPoint(this.start));
      pt.translate(this.getX(), this.getY());
      return pt;
   }

   public GPoint getEndPoint() {
      GPoint pt = this.getCTM().transform(this.getArcPoint(this.start + this.sweep));
      pt.translate(this.getX(), this.getY());
      return pt;
   }

   public void setFrameRectangle(double x, double y, double width, double height) {
      this.width = width;
      this.height = height;
      this.setLocation(x, y);
   }

   public final void setFrameRectangle(GRectangle bounds) {
      this.setFrameRectangle(bounds.getX(), bounds.getY(), bounds.getWidth(), bounds.getHeight());
   }

   public GRectangle getFrameRectangle() {
      return new GRectangle(this.getX(), this.getY(), this.width, this.height);
   }

   public void setFilled(boolean fill) {
      this.isFilled = fill;
      this.repaint();
   }

   public boolean isFilled() {
      return this.isFilled;
   }

   public void setFillColor(Color color) {
      this.fillColor = color;
      this.repaint();
   }

   public Color getFillColor() {
      return this.fillColor == null ? this.getColor() : this.fillColor;
   }

   public String paramString() {
      String tail = super.paramString();
      tail = tail.substring(tail.indexOf(41) + 1);
      GRectangle r = this.getFrameRectangle();
      String param = "frame=(" + r.getX() + ", " + r.getY() + ", " + r.getWidth() + ", " + r.getHeight() + ")";
      param = param + ", start=" + this.start + ", sweep=" + this.sweep;
      return param + tail;
   }

   protected GRectangle localBounds(GTransform ctm) {
      GRectangle bb = new GRectangle(ctm.transform(this.getArcPoint(this.start)));
      bb.add(ctm.transform(this.getArcPoint(this.start + this.sweep)));
      double rx = this.width / 2.0D;
      double ry = this.height / 2.0D;
      double a = ctm.getScaleX();
      double b = ctm.getShearY();
      double c = ctm.getShearX();
      double d = ctm.getScaleY();
      double tx = Math.atan2(c * this.height, a * this.width);
      double ty = Math.atan2(d * this.height, b * this.width);

      for(int i = 0; i < 4; ++i) {
         double t1 = tx + (double)i * 3.141592653589793D / 2.0D;
         double t2 = ty + (double)i * 3.141592653589793D / 2.0D;
         if (this.containsAngle(GMath.toDegrees(t1))) {
            bb.add(ctm.transform(rx + rx * Math.cos(t1), ry - ry * Math.sin(t1)));
         }

         if (this.containsAngle(GMath.toDegrees(t2))) {
            bb.add(ctm.transform(rx + rx * Math.cos(t2), ry - ry * Math.sin(t2)));
         }
      }

      if (this.isFilled()) {
         bb.add(ctm.transform(rx, ry));
      }

      return bb;
   }

   protected boolean localContains(double x, double y) {
      double rx = this.width / 2.0D;
      double ry = this.height / 2.0D;
      if (rx != 0.0D && ry != 0.0D) {
         double dx = x - rx;
         double dy = y - ry;
         double r = dx * dx / (rx * rx) + dy * dy / (ry * ry);
         if (this.isFilled()) {
            if (r > 1.0D) {
               return false;
            }
         } else {
            double t = 2.5D / ((rx + ry) / 2.0D);
            if (Math.abs(1.0D - r) > t) {
               return false;
            }
         }

         return this.containsAngle(GMath.toDegrees(Math.atan2(-dy, dx)));
      } else {
         return false;
      }
   }

   protected void paint2d(Graphics2D g) {
      Arc2D arc = new Double(0.0D, 0.0D, this.width, this.height, this.start, this.sweep, this.isFilled() ? 2 : 0);
      if (this.isFilled()) {
         g.setColor(this.getFillColor());
         g.fill(arc);
         g.setColor(this.getColor());
      }

      g.draw(arc);
   }

   private GPoint getArcPoint(double angle) {
      double rx = this.width / 2.0D;
      double ry = this.height / 2.0D;
      return new GPoint(rx + rx * GMath.cosDegrees(angle), ry - ry * GMath.sinDegrees(angle));
   }

   private boolean containsAngle(double theta) {
      double start = Math.min(this.getStartAngle(), this.getStartAngle() + this.getSweepAngle());
      double sweep = Math.abs(this.getSweepAngle());
      if (sweep >= 360.0D) {
         return true;
      } else {
         theta = theta < 0.0D ? 360.0D - -theta % 360.0D : theta % 360.0D;
         start = start < 0.0D ? 360.0D - -start % 360.0D : start % 360.0D;
         if (start + sweep > 360.0D) {
            return theta >= start || theta <= start + sweep - 360.0D;
         } else {
            return theta >= start && theta <= start + sweep;
         }
      }
   }
}
