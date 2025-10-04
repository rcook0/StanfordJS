package edu.stanford.cs.sjslib.core;

import edu.stanford.cs.svm.SVMClass;

public class SJSProgramClass extends SVMClass {
   public SJSProgramClass() {
      this.defineMethod("add", new Program_add());
      this.defineMethod("alert", new Program_alert());
      this.defineMethod("exit", new Program_exit());
      this.defineMethod("setConsole", new Program_setConsole());
      this.defineMethod("getConsole", new Program_getConsole());
   }
}
