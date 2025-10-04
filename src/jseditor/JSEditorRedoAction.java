package edu.stanford.cs.jseditor;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.undo.CannotUndoException;

class JSEditorRedoAction extends AbstractAction {
   private JSEditorUndoHandler handler;
   private JSEditorUndoManager manager;

   public JSEditorRedoAction(JSEditorUndoHandler handler) {
      super("Redo");
      this.setEnabled(false);
      this.handler = handler;
      this.manager = handler.getUndoManager();
   }

   public void actionPerformed(ActionEvent e) {
      try {
         this.manager.redo();
      } catch (CannotUndoException var3) {
      }

      this.update();
      this.handler.getUndoAction().update();
   }

   protected void update() {
      if (this.manager.canRedo()) {
         this.setEnabled(true);
         this.putValue("Name", this.manager.getRedoPresentationName());
      } else {
         this.setEnabled(false);
         this.putValue("Name", "Undo");
      }

   }
}
