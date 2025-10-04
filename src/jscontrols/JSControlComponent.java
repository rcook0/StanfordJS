package edu.stanford.cs.jscontrols;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JComponent;

abstract class JSControlComponent extends JComponent {
   protected abstract void paintControl(Graphics var1);

   public void paintComponent(Graphics g) {
      Graphics2D g2 = (Graphics2D)g;
      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      this.paintControl(g2);
   }
}
