package edu.stanford.cs.sjs;

import edu.stanford.cs.jsmenu.JSMenuItem;

class LargerFontItem extends JSMenuItem {
   public LargerFontItem(SJS app) {
      super("Larger Font", "^EQUALS");
      this.setActionCommand("LargerFont");
      this.addActionListener(app.getControlStrip());
   }
}
