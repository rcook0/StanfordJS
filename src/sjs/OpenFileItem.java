package edu.stanford.cs.sjs;

import edu.stanford.cs.jsmenu.JSMenuItem;

class OpenFileItem extends JSMenuItem {
   public OpenFileItem(SJS app) {
      super("Open", "O");
      this.setActionCommand("Load");
      this.addActionListener(app.getControlStrip());
   }
}
