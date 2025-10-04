package edu.stanford.cs.jsmenu;

import edu.stanford.cs.controller.Updatable;
import edu.stanford.cs.controller.Updater;
import java.awt.Toolkit;
import javax.swing.AbstractAction;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

public class JSMenuItem extends JMenuItem implements Updatable {
   private Updater updater;

   public JSMenuItem(String text) {
      this((String)text, (String)null);
   }

   public JSMenuItem(String text, String shortcut) {
      super(text);
      if (shortcut != null) {
         this.setAccelerator(parseAccelerator(shortcut));
      }

   }

   public JSMenuItem(AbstractAction action) {
      this((AbstractAction)action, (String)null);
   }

   public JSMenuItem(AbstractAction action, String shortcut) {
      super(action);
      if (shortcut != null) {
         this.setAccelerator(parseAccelerator(shortcut));
      }

   }

   public static KeyStroke parseAccelerator(String str) {
      int mask = Toolkit.getDefaultToolkit().getMenuShortcutKeyMask();
      boolean isMeta = mask == 256 || mask == 4;
      String modifiers = isMeta ? "meta" : "ctrl";
      if (str.startsWith("^")) {
         modifiers = modifiers + " shift";
         str = str.substring(1);
      }

      return KeyStroke.getKeyStroke(modifiers + " " + str);
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
