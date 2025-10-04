package edu.stanford.cs.java2js;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;

class JSTimerTask implements ActionListener, Runnable {
   private ActionEvent event;
   private ArrayList<ActionListener> listeners;

   public JSTimerTask(ArrayList<ActionListener> listeners, ActionEvent e) {
      this.listeners = listeners;
      this.event = e;
   }

   public void run() {
      this.actionPerformed((ActionEvent)null);
   }

   public void actionPerformed(ActionEvent e) {
      Iterator var3 = this.listeners.iterator();

      while(var3.hasNext()) {
         ActionListener listener = (ActionListener)var3.next();
         listener.actionPerformed(this.event);
      }

   }
}
