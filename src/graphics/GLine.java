package edu.stanford.cs.graphics;

import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Line2D.Double;

public class GLine extends GObject {
   public static final double LINE_TOLERANCE = 1.5D;
   private double dx;
   private double dy;

   public GLine(double x0, double y0, double x1, double y1) {
      this.setLocation(x0, y0);
      this.dx = x1 - x0;
      this.dy = y1 - y0;
   }

   public void setStartPoint(double x, double y) {
      GPoint pt = this.getCTM().transform(this.dx, this.dy);
      pt.setLocation(pt.getX() - x, pt.getY() - y);
      pt = this.getITM().transform(pt);
      this.dx = pt.getX();
      this.dy = pt.getY();
      this.setLocation(x, y);
   }

   public GPoint getStartPoint() {
      return this.getLocation();
   }

   public void setEndPoint(double x, double y) {
      GPoint pt = this.getITM().transform(x - this.getX(), y - this.getY());
      this.dx = pt.getX();
      this.dy = pt.getY();
      this.repaint();
   }

   public GPoint getEndPoint() {
      GPoint pt = this.getCTM().transform(this.dx, this.dy);
      return new GPoint(this.getX() + pt.getX(), this.getY() + pt.getY());
   }

   public String paramString() {
      String tail = super.paramString();
      tail = tail.substring(tail.indexOf(41) + 1);
      GPoint pt = this.getStartPoint();
      String param = "start=(" + pt.getX() + ", " + pt.getY() + ")";
      pt = this.getEndPoint();
      param = param + ", end=(" + pt.getX() + ", " + pt.getY() + ")";
      return param + tail;
   }

   protected GRectangle localBounds(GTransform ctm) {
      GRectangle bb = new GRectangle(ctm.transform(0.0D, 0.0D));
      bb.add(ctm.transform(this.dx, this.dy));
      return bb;
   }

   protected boolean localContains(double x, double y) {
      double tSquared = 2.25D;
      if (this.distanceSquared(x, y, 0.0D, 0.0D) < tSquared) {
         return true;
      } else if (this.distanceSquared(x, y, this.dx, this.dy) < tSquared) {
         return true;
      } else if (x < Math.min(0.0D, this.dx) - 1.5D) {
         return false;
      } else if (x > Math.max(0.0D, this.dx) + 1.5D) {
         return false;
      } else if (y < Math.min(0.0D, this.dy) - 1.5D) {
         return false;
      } else if (y > Math.max(0.0D, this.dy) + 1.5D) {
         return false;
      } else if ((float)this.dx == 0.0F && (float)this.dy == 0.0F) {
         return false;
      } else {
         double u = (x * this.dx + y * this.dy) / this.distanceSquared(0.0D, 0.0D, this.dx, this.dy);
         return this.distanceSquared(x, y, u * this.dx, u * this.dy) < tSquared;
      }
   }

   protected void paint2d(Graphics2D g) {
      Line2D line = new Double(0.0D, 0.0D, (double)GMath.round(this.dx), (double)GMath.round(this.dy));
      g.draw(line);
   }

   private double distanceSquared(double x0, double y0, double x1, double y1) {
      return (x1 - x0) * (x1 - x0) + (y1 - y0) * (y1 - y0);
   }
}
