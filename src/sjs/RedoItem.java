package edu.stanford.cs.sjs;

import edu.stanford.cs.jsmenu.JSMenuItem;

class RedoItem extends JSMenuItem {
   public RedoItem(SJS app) {
      super(app.getActiveEditor().getRedoAction(), "Y");
   }
}
