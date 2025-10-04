package edu.stanford.cs.graphics;

import java.awt.Graphics2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.geom.RoundRectangle2D.Double;

public class GRoundRect extends GRect {
   public static final double DEFAULT_ARC = 10.0D;
   private double aWidth;
   private double aHeight;

   public GRoundRect(double width, double height) {
      this(0.0D, 0.0D, width, height, 10.0D);
   }

   public GRoundRect(double x, double y, double width, double height) {
      this(x, y, width, height, 10.0D);
   }

   public GRoundRect(double x, double y, double width, double height, double arcSize) {
      this(x, y, width, height, arcSize, arcSize);
   }

   public GRoundRect(double x, double y, double width, double height, double arcWidth, double arcHeight) {
      super(x, y, width, height);
      this.aWidth = arcWidth;
      this.aHeight = arcHeight;
   }

   public double getArcWidth() {
      return this.aWidth;
   }

   public double getArcHeight() {
      return this.aHeight;
   }

   protected void paint2d(Graphics2D g) {
      RoundRectangle2D rr = new Double(0.0D, 0.0D, this.getFrameWidth(), this.getFrameHeight(), this.aWidth, this.aHeight);
      if (this.isFilled()) {
         g.setColor(this.getFillColor());
         g.fill(rr);
         g.setColor(this.getColor());
      }

      g.draw(rr);
   }
}
