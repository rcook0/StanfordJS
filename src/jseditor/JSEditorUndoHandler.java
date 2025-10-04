package edu.stanford.cs.jseditor;

import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.undo.UndoableEdit;

class JSEditorUndoHandler implements UndoableEditListener {
   private JSEditor editor;
   private JSEditorUndoAction undoAction;
   private JSEditorRedoAction redoAction;
   private JSEditorUndoManager undoManager;

   public JSEditorUndoHandler(JSEditor editor) {
      this.editor = editor;
      this.undoManager = new JSEditorUndoManager();
      this.undoAction = new JSEditorUndoAction(this);
      this.redoAction = new JSEditorRedoAction(this);
   }

   public void undoableEditHappened(UndoableEditEvent e) {
      if (this.editor.getUndoableFlag()) {
         this.undoManager.addEdit(e.getEdit());
         this.undoAction.update();
         this.redoAction.update();
      }

   }

   public JSEditorUndoManager getUndoManager() {
      return this.undoManager;
   }

   public JSEditorUndoAction getUndoAction() {
      return this.undoAction;
   }

   public JSEditorRedoAction getRedoAction() {
      return this.redoAction;
   }

   protected boolean isAddition(UndoableEdit edit) {
      return edit != null && edit.getPresentationName().endsWith("addition");
   }
}
