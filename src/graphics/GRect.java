package edu.stanford.cs.graphics;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;

public class GRect extends GObject implements GFillable, GResizable {
   private double width;
   private double height;
   private boolean isFilled;
   private Color fillColor;

   public GRect(double width, double height) {
      this(0.0D, 0.0D, width, height);
   }

   public GRect(double x, double y, double width, double height) {
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

   public void setSize(double width, double height) {
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

   protected GRectangle localBounds(GTransform ctm) {
      GRectangle bb = new GRectangle(ctm.transform(0.0D, 0.0D));
      bb.add(ctm.transform(this.width, 0.0D));
      bb.add(ctm.transform(0.0D, this.height));
      bb.add(ctm.transform(this.width, this.height));
      return bb;
   }

   protected boolean localContains(double x, double y) {
      return x >= 0.0D && x < this.width && y >= 0.0D && y < this.height;
   }

   protected void paint2d(Graphics2D g) {
      Rectangle2D r = new Double(0.0D, 0.0D, this.width, this.height);
      if (this.isFilled()) {
         g.setColor(this.getFillColor());
         g.fill(r);
         g.setColor(this.getColor());
      }

      g.draw(r);
   }

   protected double getFrameWidth() {
      return this.width;
   }

   protected double getFrameHeight() {
      return this.height;
   }
}
