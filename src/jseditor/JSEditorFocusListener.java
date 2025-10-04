package edu.stanford.cs.jseditor;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

class JSEditorFocusListener implements FocusListener {
   private JSEditor editor;

   public JSEditorFocusListener(JSEditor editor) {
      this.editor = editor;
   }

   public void focusGained(FocusEvent e) {
      e = new FocusEvent(this.editor, e.getID());
      FocusListener[] listeners = this.editor.getFocusListeners();
      FocusListener[] var6 = listeners;
      int var5 = listeners.length;

      for(int var4 = 0; var4 < var5; ++var4) {
         FocusListener listener = var6[var4];
         listener.focusGained(e);
      }

   }

   public void focusLost(FocusEvent e) {
      e = new FocusEvent(this.editor, e.getID());
      FocusListener[] listeners = this.editor.getFocusListeners();
      FocusListener[] var6 = listeners;
      int var5 = listeners.length;

      for(int var4 = 0; var4 < var5; ++var4) {
         FocusListener listener = var6[var4];
         listener.focusLost(e);
      }

   }
}
