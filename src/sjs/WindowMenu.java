package edu.stanford.cs.sjs;

import edu.stanford.cs.file.FileLib;
import edu.stanford.cs.jsmenu.JSMenu;
import edu.stanford.cs.jsmenu.JSMenuItem;
import java.util.Iterator;

class WindowMenu extends JSMenu {
   private SJS app;

   public WindowMenu(SJS app) {
      super("Window");
      this.app = app;
   }

   public void update() {
      this.removeAll();
      Iterator var2 = this.app.getEditors().iterator();

      while(var2.hasNext()) {
         SJSEditor editor = (SJSEditor)var2.next();
         String pathname = editor.getPathname();
         if (pathname == null) {
            pathname = "Untitled";
         }

         JSMenuItem item = new JSMenuItem(FileLib.getTail(pathname));
         item.setActionCommand("@" + pathname);
         item.addActionListener(this.app.getControlStrip());
         this.add(item);
      }

   }
}
