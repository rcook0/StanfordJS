package edu.stanford.cs.sjslib.core;

import edu.stanford.cs.svm.SVMClass;

public class SJSConsoleClass extends SVMClass {
   public SJSConsoleClass() {
      this.defineMethod("_init", new Console_init());
      this.defineMethod("new", new Console_new());
      this.defineMethod("log", new Console_log());
      this.defineMethod("print", new Console_print());
      this.defineMethod("println", new Console_println());
      this.defineMethod("showErrorMessage", new Console_showErrorMessage());
      this.defineMethod("clear", new Console_clear());
      this.defineMethod("getLine", new Console_getLine());
      this.defineMethod("getNumber", new Console_getNumber());
      this.defineMethod("getInt", new Console_getInt());
      this.defineMethod("requestInput", new Console_requestInput());
      this.defineMethod("startConsoleLog", new Console_startConsoleLog());
      this.defineMethod("endConsoleLog", new Console_endConsoleLog());
      this.defineMethod("$display", new Console_display());
   }
}
