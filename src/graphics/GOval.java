package edu.stanford.cs.graphics;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Ellipse2D.Double;

public class GOval extends GObject implements GFillable, GResizable {
   private double width;
   private double height;
   private boolean isFilled;
   private Color fillColor;

   public GOval(double width, double height) {
      this(0.0D, 0.0D, width, height);
   }

   public GOval(double x, double y, double width, double height) {
      this.width = width;
      this.height = height;
      this.setLocation(x, y);
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

   public void setBounds(GRectangle bounds) {
      this.setBounds(bounds.getX(), bounds.getY(), bounds.getWidth(), bounds.getHeight());
   }

   protected GRectangle localBounds(GTransform ctm) {
      double rx = this.width / 2.0D;
      double ry = this.height / 2.0D;
      double a = ctm.getScaleX();
      double b = ctm.getShearY();
      double c = ctm.getShearX();
      double d = ctm.getScaleY();
      double tx = Math.atan2(c * this.height, a * this.width);
      double ty = Math.atan2(d * this.height, b * this.width);
      GRectangle bb = new GRectangle(ctm.transform(rx, ry));

      for(int i = 0; i < 4; ++i) {
         double t1 = tx + (double)i * 3.141592653589793D / 2.0D;
         double t2 = ty + (double)i * 3.141592653589793D / 2.0D;
         bb.add(ctm.transform(rx + rx * Math.cos(t1), ry - ry * Math.sin(t1)));
         bb.add(ctm.transform(rx + rx * Math.cos(t2), ry - ry * Math.sin(t2)));
      }

      return bb;
   }

   protected boolean localContains(double x, double y) {
      double rx = this.width / 2.0D;
      double ry = this.height / 2.0D;
      double tx = x - rx;
      double ty = y - ry;
      return tx * tx / (rx * rx) + ty * ty / (ry * ry) <= 1.0D;
   }

   protected void paint2d(Graphics2D g) {
      Ellipse2D oval = new Double(0.0D, 0.0D, this.width, this.height);
      if (this.isFilled()) {
         g.setColor(this.getFillColor());
         g.fill(oval);
         g.setColor(this.getColor());
      }

      g.draw(oval);
   }
}
