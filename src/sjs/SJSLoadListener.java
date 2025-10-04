package edu.stanford.cs.sjs;

import edu.stanford.cs.java2js.JSEvent;
import edu.stanford.cs.java2js.JSFile;
import edu.stanford.cs.java2js.JSFileChooser;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class SJSLoadListener implements ActionListener {
   private JSFileChooser chooser;
   private SJS app;

   public SJSLoadListener(SJS app, JSFileChooser chooser) {
      this.app = app;
      this.chooser = chooser;
   }

   public void actionPerformed(ActionEvent e) {
      if (!JSEvent.isErrorEvent(e)) {
         String path = this.chooser.getPath();
         if (path != null && path.length() != 0) {
            this.app.setMainFunction((String)null);
            (new JSFile(path)).read(new SJSLoadFileListener(this.app, path));
         }
      }

   }
}
