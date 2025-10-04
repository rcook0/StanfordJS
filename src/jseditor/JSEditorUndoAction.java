package edu.stanford.cs.jseditor;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.undo.CannotUndoException;

class JSEditorUndoAction extends AbstractAction {
   private JSEditorUndoHandler handler;
   private JSEditorUndoManager manager;

   public JSEditorUndoAction(JSEditorUndoHandler handler) {
      super("Undo");
      this.setEnabled(false);
      this.handler = handler;
      this.manager = handler.getUndoManager();
   }

   public void actionPerformed(ActionEvent e) {
      try {
         this.manager.undo();
      } catch (CannotUndoException var3) {
      }

      this.update();
      this.handler.getRedoAction().update();
   }

   protected void update() {
      if (this.manager.canUndo()) {
         this.setEnabled(true);
         this.putValue("Name", this.manager.getUndoPresentationName());
      } else {
         this.setEnabled(false);
         this.putValue("Name", "Undo");
      }

   }
}
