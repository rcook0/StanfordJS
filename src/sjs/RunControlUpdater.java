package edu.stanford.cs.sjs;

import edu.stanford.cs.controller.Updatable;
import edu.stanford.cs.controller.Updater;
import edu.stanford.cs.jscontrols.RunControl;

class RunControlUpdater implements Updater {
   private SJS app;

   public RunControlUpdater(SJS app) {
      this.app = app;
   }

   public void update(Updatable obj) {
      RunControl runControl = (RunControl)obj;
      switch(this.app.getSVM().getState()) {
      case 1:
      case 3:
         runControl.setRunning(true);
         runControl.setEnabled(true);
         break;
      case 2:
      default:
         runControl.setRunning(false);
         runControl.setEnabled(this.app.isRunEnabled());
      }

   }
}
