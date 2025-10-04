package edu.stanford.cs.graphics;

import java.awt.Component;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

public class GLabel extends GObject {
   public static final Font DEFAULT_FONT = new Font("Default", 0, 12);
   private String label;
   private Font labelFont;
   private static final Component DUMMY_COMPONENT = GImageTools.getImageObserver();

   public GLabel(String str) {
      this(str, 0.0D, 0.0D);
   }

   public GLabel(String str, double x, double y) {
      this.label = str;
      this.setFont(DEFAULT_FONT);
      this.setLocation(x, y);
   }

   public void setFont(Font font) {
      this.labelFont = font;
      this.repaint();
   }

   public void setFont(String str) {
      this.setFont(Font.decode(str));
   }

   public Font getFont() {
      return this.labelFont;
   }

   public void setLabel(String str) {
      this.label = str;
      this.repaint();
   }

   public String getLabel() {
      return this.label;
   }

   protected void paint2d(Graphics2D g) {
      g.setFont(this.labelFont);
      g.drawString(this.label, 0, 0);
   }

   public double getAscent() {
      return (double)this.getFontMetrics().getAscent();
   }

   public double getDescent() {
      return (double)this.getFontMetrics().getDescent();
   }

   public FontMetrics getFontMetrics() {
      Component comp = this.getComponent();
      if (comp == null) {
         comp = DUMMY_COMPONENT;
      }

      return comp.getFontMetrics(this.labelFont);
   }

   public String paramString() {
      return super.paramString() + ", string=\"" + this.label + "\"";
   }

   protected GRectangle localBounds(GTransform ctm) {
      FontMetrics fm = this.getFontMetrics();
      double y0 = (double)(-fm.getAscent());
      double width = (double)fm.stringWidth(this.label);
      double height = (double)fm.getHeight();
      GRectangle bb = new GRectangle(ctm.transform(0.0D, y0));
      bb.add(ctm.transform(width, y0));
      bb.add(ctm.transform(0.0D, y0 + height));
      bb.add(ctm.transform(width, y0 + height));
      return bb;
   }

   protected boolean localContains(double x, double y) {
      FontMetrics fm = this.getFontMetrics();
      double y0 = (double)(-fm.getAscent());
      double width = (double)fm.stringWidth(this.label);
      double height = (double)fm.getHeight();
      return x >= 0.0D && x < width && y >= y0 && y < y0 + height;
   }
}
