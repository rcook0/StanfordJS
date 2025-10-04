package edu.stanford.cs.sjs;

import edu.stanford.cs.svm.SVMConsoleListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

class SJSInterpreterMonitor extends SVMConsoleListener implements SJSCommandMonitor, ChangeListener {
   private SJSInterpreter interpreter;
   private SJSVM svm;
   private boolean interactive;
   private boolean waiting;

   public SJSInterpreterMonitor(SJSInterpreter interpreter) {
      super(interpreter.getSVM());
      this.interpreter = interpreter;
      this.svm = (SJSVM)interpreter.getSVM();
      this.svm.addChangeListener(this);
      this.interactive = false;
      this.waiting = false;
   }

   public void waitForExit() {
      synchronized(this.interpreter) {
         try {
            this.interpreter.wait();
         } catch (InterruptedException var3) {
         }

      }
   }

   public void waitForCompletion() {
      synchronized(this) {
         while(!this.svm.isFinished()) {
            try {
               this.waiting = true;
               this.wait();
               this.waiting = false;
            } catch (InterruptedException var3) {
            }
         }

         if (this.interactive) {
            this.interpreter.signalFinished();
         }

      }
   }

   public void setInteractive(boolean flag) {
      this.interactive = flag;
   }

   public boolean isInteractive() {
      return this.interactive;
   }

   protected void processConsoleLine(String line) {
      this.interpreter.processConsoleLine(line);
   }

   public void showError(String msg, int line) {
      System.err.println(line + ": " + msg);
   }

   public void update() {
   }

   public void signalFinished() {
      synchronized(this) {
         this.notify();
      }
   }

   public void stateChanged(ChangeEvent e) {
      if (this.svm.isFinished()) {
         if (this.waiting) {
            this.signalFinished();
         } else if (this.interactive) {
            this.interpreter.signalFinished();
         }
      }

   }
}
