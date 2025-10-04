package edu.stanford.cs.sjs;

import edu.stanford.cs.jsmenu.JSMenuItem;

class ClearConsoleItem extends JSMenuItem {
   public ClearConsoleItem(SJS app) {
      super("Clear Console");
      this.setActionCommand("ClearConsole");
      this.addActionListener(app.getControlStrip());
   }
}
