package edu.stanford.cs.java2js;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JComponent;

class JSTitleBarListener implements MouseListener {
   private JComponent target;

   public JSTitleBarListener(JComponent target) {
      this.target = target;
   }

   public void mouseClicked(MouseEvent e) {
      this.target.requestFocus();
   }

   public void mouseEntered(MouseEvent e) {
   }

   public void mouseExited(MouseEvent e) {
   }

   public void mousePressed(MouseEvent e) {
   }

   public void mouseReleased(MouseEvent e) {
   }
}
