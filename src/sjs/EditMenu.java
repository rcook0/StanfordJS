package edu.stanford.cs.sjs;

import edu.stanford.cs.jsmenu.JSMenu;

class EditMenu extends JSMenu {
   public EditMenu(SJS app) {
      super("Edit");
      this.add(new CutItem(app));
      this.add(new CopyItem(app));
      this.add(new PasteItem(app));
      this.add(new SelectAllItem(app));
      this.addSeparator();
      this.add(new FindReplaceItem(app));
      this.addSeparator();
      this.add(new UndoItem(app));
      this.add(new RedoItem(app));
   }
}
