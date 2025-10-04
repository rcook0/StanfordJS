package edu.stanford.cs.sjs;

import edu.stanford.cs.jsmenu.JSMenuItem;

class SmallerFontItem extends JSMenuItem {
   public SmallerFontItem(SJS app) {
      super("Smaller Font", "MINUS");
      this.setActionCommand("SmallerFont");
      this.addActionListener(app.getControlStrip());
   }
}
