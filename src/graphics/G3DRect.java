package edu.stanford.cs.graphics;

import java.awt.Graphics;
import java.awt.Graphics2D;

public class G3DRect extends GRect {
   private boolean isRaised;

   public G3DRect(double width, double height) {
      this(0.0D, 0.0D, width, height, false);
   }

   public G3DRect(double x, double y, double width, double height) {
      this(x, y, width, height, false);
   }

   public G3DRect(double x, double y, double width, double height, boolean raised) {
      super(x, y, width, height);
      this.isRaised = raised;
   }

   public void paint(Graphics g) {
      if (this.isVisible()) {
         Graphics2D g2d = this.createTransformedGraphics(g);
         if (this.isFilled()) {
            g2d.setColor(this.getFillColor());
            g2d.fill3DRect(0, 0, GMath.round(this.getFrameWidth()), GMath.round(this.getFrameHeight()), this.isRaised);
            g2d.setColor(this.getColor());
         }

         g2d.draw3DRect(0, 0, GMath.round(this.getFrameWidth()), GMath.round(this.getFrameHeight()), this.isRaised);
         g2d.dispose();
      }
   }

   public void setRaised(boolean raised) {
      this.isRaised = raised;
   }

   public boolean isRaised() {
      return this.isRaised;
   }
}
