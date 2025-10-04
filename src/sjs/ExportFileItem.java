package edu.stanford.cs.sjs;

import edu.stanford.cs.jsmenu.JSMenuItem;

class ExportFileItem extends JSMenuItem {
   public ExportFileItem(SJS app) {
      super("Export");
      this.setActionCommand("Export");
      this.addActionListener(app.getControlStrip());
   }
}
