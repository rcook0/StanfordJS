package edu.stanford.cs.sjs;

import edu.stanford.cs.java2js.JSEvent;
import edu.stanford.cs.java2js.JSFile;
import edu.stanford.cs.java2js.JSFileChooser;
import edu.stanford.cs.svm.SVMModule;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class SJSSaveListener implements ActionListener {
   private JSFileChooser chooser;
   private SJS app;

   public SJSSaveListener(SJS app, JSFileChooser chooser) {
      this.app = app;
      this.chooser = chooser;
   }

   public void actionPerformed(ActionEvent e) {
      if (!JSEvent.isErrorEvent(e)) {
         if (this.chooser.getDialogTitle().equals("Save")) {
            String path = this.chooser.getPath();
            if (path != null && path.length() != 0) {
               SJSEditor editor = this.app.getActiveEditor();
               String text = editor.getText();
               (new JSFile(path)).write(text, new SJSSaveFileListener(this.app, path));
            }
         } else if (this.chooser.getDialogTitle().equals("Export")) {
            SVMModule module = this.app.getModule();
            if (module != null) {
               SJSInterpreter.exportAsJS(this.chooser.getPath(), module);
            }
         }
      }

   }
}
