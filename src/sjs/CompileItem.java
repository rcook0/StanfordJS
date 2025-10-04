package edu.stanford.cs.sjs;

import edu.stanford.cs.jsmenu.JSMenuItem;

class CompileItem extends JSMenuItem {
   public CompileItem(SJS app) {
      super("Compile", "K");
      this.setUpdater(app.createCompileUpdater());
      this.setActionCommand("Compile");
      this.addActionListener(app.getControlStrip());
   }
}
