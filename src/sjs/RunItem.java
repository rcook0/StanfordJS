package edu.stanford.cs.sjs;

import edu.stanford.cs.jsmenu.JSMenuItem;

class RunItem extends JSMenuItem {
   public RunItem(SJS app) {
      super("Run", "R");
      this.setUpdater(app.createRunUpdater());
      this.setActionCommand("Run");
      this.addActionListener(app.getControlStrip());
   }
}
