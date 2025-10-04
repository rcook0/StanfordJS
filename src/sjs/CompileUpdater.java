package edu.stanford.cs.sjs;

import edu.stanford.cs.controller.Updatable;
import edu.stanford.cs.controller.Updater;

class CompileUpdater implements Updater {
   private SJS app;

   public CompileUpdater(SJS app) {
      this.app = app;
   }

   public void update(Updatable obj) {
      obj.setEnabled(this.app.isCompileEnabled());
   }
}
