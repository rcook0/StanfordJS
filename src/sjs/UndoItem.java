package edu.stanford.cs.sjs;

import edu.stanford.cs.jsmenu.JSMenuItem;

class UndoItem extends JSMenuItem {
   public UndoItem(SJS app) {
      super(app.getActiveEditor().getUndoAction(), "Z");
   }
}
