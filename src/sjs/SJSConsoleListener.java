package edu.stanford.cs.sjs;

import edu.stanford.cs.svm.SVMConsoleListener;

class SJSConsoleListener extends SVMConsoleListener {
   private SJS app;

   public SJSConsoleListener(SJS app) {
      super(app.getSVM());
      this.app = app;
   }

   protected void processConsoleLine(String line) {
      this.app.processConsoleLine(line);
   }
}
