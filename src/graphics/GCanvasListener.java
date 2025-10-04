package edu.stanford.cs.graphics;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

class GCanvasListener implements MouseListener, MouseMotionListener {
   private GCanvas gCanvas;

   public GCanvasListener(GCanvas gc) {
      this.gCanvas = gc;
   }

   public void mouseClicked(MouseEvent e) {
      this.gCanvas.dispatchMouseEvent(e);
   }

   public void mousePressed(MouseEvent e) {
      this.gCanvas.requestFocus();
      this.gCanvas.dispatchMouseEvent(e);
   }

   public void mouseReleased(MouseEvent e) {
      this.gCanvas.dispatchMouseEvent(e);
   }

   public void mouseEntered(MouseEvent e) {
      this.gCanvas.dispatchMouseEvent(e);
   }

   public void mouseExited(MouseEvent e) {
      this.gCanvas.dispatchMouseEvent(e);
   }

   public void mouseDragged(MouseEvent e) {
      this.gCanvas.dispatchMouseEvent(e);
   }

   public void mouseMoved(MouseEvent e) {
      this.gCanvas.dispatchMouseEvent(e);
   }
}
