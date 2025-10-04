package edu.stanford.cs.jseditor;

import javax.swing.undo.UndoManager;
import javax.swing.undo.UndoableEdit;

class JSEditorUndoManager extends UndoManager {
   public UndoableEdit getLastEdit() {
      return this.editToBeUndone();
   }
}
