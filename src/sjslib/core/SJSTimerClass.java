package edu.stanford.cs.sjslib.core;

import edu.stanford.cs.svm.SVMClass;

public class SJSTimerClass extends SVMClass {
   public SJSTimerClass() {
      this.defineMethod("new", new Timer_new());
      this.defineMethod("clearTimeout", new Timer_clearTimeout());
      this.defineMethod("clearInterval", new Timer_clearInterval());
      this.defineMethod("setTimeout", new Timer_setTimeout());
      this.defineMethod("setInterval", new Timer_setInterval());
      this.defineMethod("setRepeats", new Timer_setRepeats());
      this.defineMethod("setActionCommand", new Timer_setActionCommand());
      this.defineMethod("start", new Timer_start());
      this.defineMethod("stop", new Timer_stop());
      this.defineMethod("pause", new Timer_pause());
   }
}
