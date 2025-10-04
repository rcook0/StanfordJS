package edu.stanford.cs.sjs;

import edu.stanford.cs.jsmenu.JSMenuItem;

class FindReplaceItem extends JSMenuItem {
   public FindReplaceItem(SJS app) {
      super("Find/Replace", "F");
      this.addActionListener(app.getControlStrip());
   }
}
