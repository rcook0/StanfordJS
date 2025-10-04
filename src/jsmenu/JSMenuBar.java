package edu.stanford.cs.jsmenu;

import edu.stanford.cs.controller.Updatable;
import edu.stanford.cs.controller.Updater;

public class JSMenuBar extends JMenuBarWrapper implements Updatable {
   private Updater updater = null;

   public JSMenuBar() {
      super(initMenuBarProperties());
   }

   public void update() {
      int n = this.getMenuCount();

      for(int i = 0; i < n; ++i) {
         Updatable menu = (Updatable)this.getMenu(i);
         if (menu != null) {
            menu.update();
         }
      }

      if (this.updater != null) {
         this.updater.update(this);
      }

   }

   public void setUpdater(Updater updater) {
      this.updater = updater;
   }

   private static int initMenuBarProperties() {
      System.setProperty("apple.laf.useScreenMenuBar", "true");
      return 0;
   }
}
