package edu.stanford.cs.sjs;

import edu.stanford.cs.svm.SVM;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

class SJSStateChangeListener implements ChangeListener {
   private SJS app;

   public SJSStateChangeListener(SJS app) {
      this.app = app;
   }

   public void stateChanged(ChangeEvent e) {
      SVM svm = this.app.getSVM();
      SJSEditor editor = this.app.getActiveEditor();
      switch(svm.getState()) {
      case 4:
      case 7:
         int line = editor.getSourceLineIndex(svm.getStatementOffset());
         editor.setCurrentLine(line);
         break;
      case 5:
         editor.setCurrentLine(0);
      case 6:
      }

   }
}
