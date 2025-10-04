package edu.stanford.cs.sjs;

public interface SJSCommandMonitor {
   void showError(String var1, int var2);

   void update();

   void signalFinished();
}
