package edu.stanford.cs.jsconsole;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

class JSConsoleFocusListener implements FocusListener {
   private JSConsole console;

   public JSConsoleFocusListener(JSConsole console) {
      this.console = console;
   }

   public void focusGained(FocusEvent e) {
      e = new FocusEvent(this.console, e.getID());
      FocusListener[] listeners = this.console.getFocusListeners();
      FocusListener[] var6 = listeners;
      int var5 = listeners.length;

      for(int var4 = 0; var4 < var5; ++var4) {
         FocusListener listener = var6[var4];
         listener.focusGained(e);
      }

   }

   public void focusLost(FocusEvent e) {
      e = new FocusEvent(this.console, e.getID());
      FocusListener[] listeners = this.console.getFocusListeners();
      FocusListener[] var6 = listeners;
      int var5 = listeners.length;

      for(int var4 = 0; var4 < var5; ++var4) {
         FocusListener listener = var6[var4];
         listener.focusLost(e);
      }

   }
}
