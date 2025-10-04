package edu.stanford.cs.sjs;

import edu.stanford.cs.controller.Updatable;
import edu.stanford.cs.controller.Updater;

class RunUpdater implements Updater {
   private SJS app;

   public RunUpdater(SJS app) {
      this.app = app;
   }

   public void update(Updatable obj) {
      switch(this.app.getSVM().getState()) {
      case 1:
      case 3:
         obj.setEnabled(false);
         break;
      case 2:
      default:
         obj.setEnabled(this.app.isRunEnabled());
      }

   }
}
