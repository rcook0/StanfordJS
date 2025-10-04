package edu.stanford.cs.java2js;

import java.awt.Component;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JComponent;

public class JSCanvas extends JComponent {
   private double sf = 1.0D;

   public void setScaleFactor(double sf) {
      this.sf = sf;
   }

   public double getScaleFactor() {
      return this.sf;
   }

   public void paint(Graphics g) {
      Graphics2D g2d = (Graphics2D)g.create();
      g.setColor(this.getBackground());
      g.fillRect(0, 0, this.getWidth(), this.getHeight());
      g2d.scale(this.sf, this.sf);
      super.paint(g2d);
      g2d.dispose();
   }

   public static Frame getEnclosingFrame(Component comp) {
      while(comp != null && !(comp instanceof Frame)) {
         comp = ((Component)comp).getParent();
      }

      return (Frame)comp;
   }
}
