package edu.stanford.cs.sjs;

import edu.stanford.cs.jsmenu.JSMenuItem;

class StopItem extends JSMenuItem {
   public StopItem(SJS app) {
      super("Stop", "PERIOD");
      this.setActionCommand("Stop");
      this.addActionListener(app.getControlStrip());
   }
}
