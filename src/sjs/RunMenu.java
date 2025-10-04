package edu.stanford.cs.sjs;

import edu.stanford.cs.jsmenu.JSMenu;

class RunMenu extends JSMenu {
   public RunMenu(SJS app) {
      super("Run");
      this.add(new CompileItem(app));
      this.add(new RunItem(app));
      this.add(new StopItem(app));
      this.add(new ResetItem(app));
      this.addSeparator();
      this.add(new StepItem(app));
      this.add(new StepOverItem(app));
   }
}
