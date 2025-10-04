package edu.stanford.cs.java2js;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

class JSErrorDialogListener implements MouseListener, MouseMotionListener {
   private JSErrorDialog dialog;
   private int deltaX;
   private int deltaY;

   public JSErrorDialogListener(JSErrorDialog dialog) {
      this.dialog = dialog;
   }

   public void mouseClicked(MouseEvent e) {
      this.dialog.execute("Cancel");
      this.dialog.getTarget().requestFocus();
      e.consume();
   }

   public void mouseEntered(MouseEvent e) {
   }

   public void mouseExited(MouseEvent e) {
   }

   public void mousePressed(MouseEvent e) {
      this.deltaX = this.dialog.getX() - e.getX();
      this.deltaY = this.dialog.getY() - e.getY();
      e.consume();
   }

   public void mouseReleased(MouseEvent e) {
      e.consume();
   }

   public void mouseMoved(MouseEvent e) {
      e.consume();
   }

   public void mouseDragged(MouseEvent e) {
      this.dialog.setLocation(e.getX() + this.deltaX, e.getY() + this.deltaY);
      e.consume();
   }
}
