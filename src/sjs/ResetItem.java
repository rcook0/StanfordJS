package edu.stanford.cs.sjs;

import edu.stanford.cs.jsmenu.JSMenuItem;

class ResetItem extends JSMenuItem {
   public ResetItem(SJS app) {
      super("Reset", "^R");
      this.setActionCommand("Reset");
      this.addActionListener(app.getControlStrip());
   }
}
