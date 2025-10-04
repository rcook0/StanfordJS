package edu.stanford.cs.sjs;

import edu.stanford.cs.jsmenu.JSMenuItem;

class StepItem extends JSMenuItem {
   public StepItem(SJS app) {
      super("Step", "T");
      this.setUpdater(app.createStepUpdater());
      this.setActionCommand("Step");
      this.addActionListener(app.getControlStrip());
   }
}
