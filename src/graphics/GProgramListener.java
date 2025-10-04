package edu.stanford.cs.graphics;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

class GProgramListener implements ComponentListener, MouseListener, Runnable {
   private GWindow gw;
   private Thread mainThread;
   private boolean clickFlag;

   public GProgramListener(GWindow gw) {
      this.gw = gw;
      this.mainThread = Thread.currentThread();
      (new Thread(this)).start();
   }

   public synchronized void waitForClick() {
      this.clickFlag = false;

      while(!this.clickFlag) {
         try {
            this.wait();
         } catch (InterruptedException var2) {
         }
      }

   }

   public void run() {
      try {
         this.mainThread.join();
      } catch (InterruptedException var2) {
      }

      this.gw.repaint();
   }

   public void componentHidden(ComponentEvent e) {
   }

   public void componentMoved(ComponentEvent e) {
   }

   public void componentResized(ComponentEvent e) {
      this.gw.repaint();
   }

   public void componentShown(ComponentEvent e) {
      this.gw.repaint();
   }

   public void mouseClicked(MouseEvent e) {
      this.signalClickOccurred();
   }

   public void mouseEntered(MouseEvent e) {
   }

   public void mouseExited(MouseEvent e) {
   }

   public void mousePressed(MouseEvent e) {
   }

   public void mouseReleased(MouseEvent e) {
   }

   private synchronized void signalClickOccurred() {
      this.clickFlag = true;
      this.notifyAll();
   }
}
