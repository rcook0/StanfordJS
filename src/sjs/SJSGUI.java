package edu.stanford.cs.sjs;

class SJSGUI implements Runnable {
   private boolean trace;
   private boolean traceErrors;

   public SJSGUI(boolean trace, boolean traceErrors) {
      this.trace = trace;
      this.traceErrors = traceErrors;
   }

   public void run() {
      SJS sjs = new SJS();
      sjs.setTraceFlag(this.trace);
      sjs.getSVM().setTraceErrors(this.traceErrors);
      sjs.startAfterSetup("SJS");
   }
}
