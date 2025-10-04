package edu.stanford.cs.jsmenu;

import edu.stanford.cs.controller.Updatable;
import edu.stanford.cs.controller.Updater;
import javax.swing.JMenuItem;

public class JSCheckBoxMenuItem extends JMenuItem implements Updatable {
   private Updater updater;

   public JSCheckBoxMenuItem(String text) {
      this(text, (String)null);
   }

   public JSCheckBoxMenuItem(String text, String shortcut) {
      super(text);
      if (shortcut != null) {
         this.setAccelerator(JSMenuItem.parseAccelerator(shortcut));
      }

   }

   public void update() {
      if (this.updater != null) {
         this.updater.update(this);
      }

   }

   public void setUpdater(Updater updater) {
      this.updater = updater;
   }
}
