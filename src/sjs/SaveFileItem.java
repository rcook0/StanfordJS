package edu.stanford.cs.sjs;

import edu.stanford.cs.jsmenu.JSMenuItem;

class SaveFileItem extends JSMenuItem {
   public SaveFileItem(SJS app) {
      super("Save", "S");
      this.setActionCommand("Save");
      this.addActionListener(app.getControlStrip());
   }
}
