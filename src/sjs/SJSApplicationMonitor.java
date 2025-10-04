package edu.stanford.cs.sjs;

class SJSApplicationMonitor implements SJSCommandMonitor {
   private SJS app;

   public SJSApplicationMonitor(SJS app) {
      this.app = app;
   }

   public void showError(String msg, int line) {
      this.app.showError(msg, line);
   }

   public void update() {
      this.app.updateControls();
   }

   public void signalFinished() {
      this.app.signalFinished();
   }
}
