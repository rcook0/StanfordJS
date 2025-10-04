package edu.stanford.cs.sjs;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

class SJSEditorListener implements ChangeListener {
   private SJS app;

   public SJSEditorListener(SJS app) {
      this.app = app;
   }

   public void stateChanged(ChangeEvent e) {
      this.app.updateControls();
   }
}
