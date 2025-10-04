package edu.stanford.cs.sjs;

import edu.stanford.cs.jsmenu.JSMenuBar;

public class SJSMenuBar extends JSMenuBar {
   public SJSMenuBar(SJS app) {
      this.add(new FileMenu(app));
      this.add(new EditMenu(app));
      this.add(new ViewMenu(app));
      this.add(new WindowMenu(app));
      this.add(new RunMenu(app));
   }
}
