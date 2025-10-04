package edu.stanford.cs.java2js;

import java.awt.Graphics;
import javax.swing.JScrollBar;

class JSScrollBar extends JScrollBar {
   public JSScrollBar(int orientation) {
      super(orientation);
   }

   public void paint(Graphics g) {
      boolean isPSGraphics = false;

      try {
         Class<?> psGraphics = Class.forName("edu.stanford.cs.psgraphics.PSGraphics");
         isPSGraphics = psGraphics.isInstance(g);
      } catch (Exception var4) {
      }

      if (!isPSGraphics) {
         super.paint(g);
      }

   }
}
