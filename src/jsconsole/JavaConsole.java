package edu.stanford.cs.jsconsole;

import edu.stanford.cs.java2js.JSEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class JavaConsole implements NBConsole {
   private ArrayList<ActionListener> listeners = new ArrayList();
   private StringBuffer buffer = null;

   public void clear() {
      if (this.buffer != null) {
         this.buffer = new StringBuffer();
      }

   }

   public boolean isSwingComponent() {
      return false;
   }

   public void print(Object value) {
      if (this.buffer == null) {
         System.out.print(value);
      } else {
         this.buffer.append(value);
      }

   }

   public void println() {
      if (this.buffer == null) {
         System.out.println();
      } else {
         this.buffer.append("\n");
      }

   }

   public void println(Object value) {
      if (this.buffer == null) {
         System.out.println(value);
      } else {
         this.buffer.append(value + "\n");
      }

   }

   public void log(Object value) {
      this.println(value);
   }

   public void showErrorMessage(String msg) {
      System.err.println(msg);
   }

   public void requestInput(String prompt) {
      System.out.print(prompt);
      Thread t = new Thread(new JavaConsoleMonitor(this));
      t.setDaemon(true);
      t.start();
   }

   public void forceInput(String input) {
      this.println(input);
      this.processInput(input);
   }

   public void addActionListener(ActionListener listener) {
      this.listeners.add(listener);
   }

   public void startConsoleLog() {
      this.buffer = new StringBuffer();
   }

   public String endConsoleLog() {
      String log = this.buffer.toString();
      this.buffer = null;
      return log;
   }

   public void processInput(String str) {
      ActionEvent e = new ActionEvent(this, 1001, str);
      JSEvent.dispatchList(this.listeners, e);
   }
}
