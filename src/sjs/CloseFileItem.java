package edu.stanford.cs.sjs;

import edu.stanford.cs.jsmenu.JSMenuItem;

class CloseFileItem extends JSMenuItem {
   private SJS app;

   public CloseFileItem(SJS app) {
      super("Close", "W");
      this.app = app;
      this.setActionCommand("Close");
      this.addActionListener(app.getControlStrip());
   }

   public void update() {
      this.setEnabled(this.app.isCloseEnabled());
   }
}
