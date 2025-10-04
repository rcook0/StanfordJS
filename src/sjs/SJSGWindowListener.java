package edu.stanford.cs.sjs;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

class SJSGWindowListener implements WindowListener {
   private SJSGWindow window;

   public SJSGWindowListener(SJSGWindow window) {
      this.window = window;
   }

   public void windowActivated(WindowEvent e) {
   }

   public void windowClosed(WindowEvent e) {
   }

   public void windowClosing(WindowEvent e) {
      this.window.getSVM().closeGWindow(this.window);
   }

   public void windowDeactivated(WindowEvent e) {
   }

   public void windowDeiconified(WindowEvent e) {
   }

   public void windowIconified(WindowEvent e) {
   }

   public void windowOpened(WindowEvent e) {
   }
}
