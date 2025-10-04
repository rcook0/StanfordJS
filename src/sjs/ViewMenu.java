package edu.stanford.cs.sjs;

import edu.stanford.cs.jsmenu.JSMenu;

class ViewMenu extends JSMenu {
   public ViewMenu(SJS app) {
      super("View");
      this.add(new LargerFontItem(app));
      this.add(new SmallerFontItem(app));
      this.addSeparator();
      this.add(new ClearConsoleItem(app));
   }
}
