package edu.stanford.cs.sjs;

import edu.stanford.cs.java2js.JSFile;
import edu.stanford.cs.java2js.JSFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class SJSSaveFileListener implements ActionListener {
   private SJS app;
   private String path;

   public SJSSaveFileListener(SJS app, String path) {
      this.app = app;
      this.path = path;
   }

   public void actionPerformed(ActionEvent e) {
      SJSEditor editor = this.app.getActiveEditor();
      JSFrame frame = editor.getFrame();
      if (frame != null) {
         frame.setTitle(JSFile.getTail(this.path));
      }

   }
}
