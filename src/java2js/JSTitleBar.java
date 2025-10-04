package edu.stanford.cs.java2js;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.JComponent;

public class JSTitleBar extends JComponent implements FocusListener {
   public static final int HEIGHT = 24;
   public static final Color TOP_FOCUSED = new Color(13421772);
   public static final Color BOTTOM_FOCUSED = new Color(10066329);
   public static final Color TOP_BLURRED = new Color(15658734);
   public static final Color BOTTOM_BLURRED = new Color(12303291);
   public static final int TITLE_DY = -1;
   private String title;
   private boolean componentHasFocus;

   public JSTitleBar(String title) {
      this.title = title;
      if (JSPlatform.isMacOSX()) {
         this.setFont(Font.decode("Lucida Grande-Bold-12"));
      } else {
         this.setFont(Font.decode("System-12"));
      }

      this.componentHasFocus = false;
   }

   public void setTitle(String title) {
      this.title = title;
      this.repaint();
   }

   public String getTitle() {
      return this.title;
   }

   public Dimension getPreferredSize() {
      return this.getMinimumSize();
   }

   public Dimension getMinimumSize() {
      FontMetrics fm = this.getFontMetrics(this.getFont());
      return new Dimension(fm.stringWidth(this.title), 24);
   }

   public void focusGained(FocusEvent e) {
      this.componentHasFocus = true;
      this.repaint();
   }

   public void focusLost(FocusEvent e) {
      this.componentHasFocus = false;
      this.repaint();
   }

   public void paintComponent(Graphics g) {
      Graphics2D g2 = (Graphics2D)g;
      Color c1 = this.componentHasFocus ? TOP_FOCUSED : TOP_BLURRED;
      Color c2 = this.componentHasFocus ? BOTTOM_FOCUSED : BOTTOM_BLURRED;
      g2.setPaint(new GradientPaint(0.0F, 0.0F, c1, 0.0F, (float)this.getHeight(), c2));
      g2.fillRect(0, 0, this.getWidth(), this.getHeight());
      g2.setColor(Color.DARK_GRAY);
      g2.setFont(this.getFont());
      RenderingHints rh = new RenderingHints(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
      g2.setRenderingHints(rh);
      FontMetrics fm = g2.getFontMetrics();
      int x = (this.getWidth() - fm.stringWidth(this.title)) / 2;
      int y = (this.getHeight() + fm.getAscent()) / 2 + -1;
      g2.drawString(this.title, x, y);
      g2.setColor(JSFrame.BORDER_COLOR);
      g2.drawRect(0, 0, this.getWidth() - 1, this.getHeight() + 1);
   }
}
