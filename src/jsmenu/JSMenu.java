package edu.stanford.cs.jsmenu;

import edu.stanford.cs.controller.Updatable;
import edu.stanford.cs.controller.Updater;
import javax.swing.JMenu;

public class JSMenu extends JMenu implements Updatable {
   private Updater updater = null;

   public JSMenu(String name) {
      super(name);
   }

   public void update() {
      int n = this.getItemCount();

      for(int i = 0; i < n; ++i) {
         Updatable item = (Updatable)this.getItem(i);
         if (item != null) {
            item.update();
         }
      }

      if (this.updater != null) {
         this.updater.update(this);
      }

   }

   public void setUpdater(Updater updater) {
      this.updater = updater;
   }
}
