package edu.stanford.cs.java2js;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.Timer;

public class JSEvent {
   private static boolean useHeadlessTimer = false;

   public static void dispatch(ActionListener listener, ActionEvent e) {
      ArrayList<ActionListener> listeners = new ArrayList();
      listeners.add(listener);
      dispatchList(listeners, e);
   }

   public static void dispatchList(ArrayList<ActionListener> listeners, ActionEvent e) {
      JSTimerTask task = new JSTimerTask(listeners, e);
      if (useHeadlessTimer) {
         (new Thread(task)).start();
      } else {
         Timer swingTimer = new Timer(0, task);
         swingTimer.setRepeats(false);
         swingTimer.start();
      }

   }

   public static ActionEvent createActionEvent(Object source, String command) {
      return new ActionEvent(source, 1001, command);
   }

   public static ActionEvent createActionEvent(Object source, String command, int modifiers) {
      return new ActionEvent(source, 1001, command, modifiers);
   }

   public static boolean isErrorEvent(ActionEvent e) {
      return e instanceof JSErrorEvent;
   }

   public static void setHeadlessTimer(boolean flag) {
      useHeadlessTimer = flag;
      System.setProperty("java.awt.headless", "" + flag);
   }

   public static boolean getHeadlessTimer() {
      return useHeadlessTimer;
   }
}
