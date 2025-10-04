package edu.stanford.cs.programeditor;

import edu.stanford.cs.java2js.JSErrorDialog;

public class ProgramErrorDialog extends JSErrorDialog {
   private ProgramEditor editor;

   public ProgramErrorDialog(ProgramEditor editor) {
      super(editor, false);
      this.editor = editor;
   }

   public void execute(String cmd) {
      this.editor.dismissErrorDialog();
   }
}
