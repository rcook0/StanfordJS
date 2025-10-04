package edu.stanford.cs.sjs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class SJSLoadFileListener implements ActionListener {
   private SJS app;
   private String path;

   public SJSLoadFileListener(SJS app, String path) {
      this.app = app;
      this.path = path;
   }

   public void actionPerformed(ActionEvent e) {
      this.app.openEditor(this.path, e.getActionCommand());
   }
}
