package edu.stanford.cs.sjs;

import edu.stanford.cs.jsmenu.JSMenuItem;

class StepOverItem extends JSMenuItem {
   public StepOverItem(SJS app) {
      super("Step Over", "^T");
      this.setUpdater(app.createStepUpdater());
      this.setActionCommand("StepOver");
      this.addActionListener(app.getControlStrip());
   }
}
