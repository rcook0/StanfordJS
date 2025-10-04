package edu.stanford.cs.sjs;

import edu.stanford.cs.jsmenu.JSMenuItem;

class NewFileItem extends JSMenuItem {
   public NewFileItem(SJS app) {
      super("New", "N");
      this.setActionCommand("New");
      this.addActionListener(app.getControlStrip());
   }
}
