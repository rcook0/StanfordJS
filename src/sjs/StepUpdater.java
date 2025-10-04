package edu.stanford.cs.sjs;

import edu.stanford.cs.controller.Updatable;
import edu.stanford.cs.controller.Updater;

class StepUpdater implements Updater {
   private SJS app;

   public StepUpdater(SJS app) {
      this.app = app;
   }

   public void update(Updatable obj) {
      obj.setEnabled(this.app.isStepEnabled());
   }
}
