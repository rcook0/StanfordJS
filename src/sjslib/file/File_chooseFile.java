package edu.stanford.cs.sjslib.file;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.java2js.JSEvent;
import edu.stanford.cs.sjs.SJS;
import edu.stanford.cs.sjs.SJSVM;
import edu.stanford.cs.svm.SVM;
import edu.stanford.cs.svm.SVMMethod;
import java.awt.Component;
import java.io.File;
import javax.swing.JFileChooser;

class File_chooseFile extends SVMMethod {
   public void execute(SVM svm, Value receiver) {
      Value callback = null;
      Value cancel = null;
      if (svm.getArgumentCount() == 2) {
         svm.checkSignature("File.chooseFile", "**");
         cancel = svm.pop();
         callback = svm.pop();
      } else {
         svm.checkSignature("File.chooseFile", "*");
         callback = svm.pop();
      }

      JSEvent.setHeadlessTimer(false);
      JFileChooser chooser = new JFileChooser();
      String cwd = ".";
      SJS app = ((SJSVM)svm).getApplication();
      if (app != null) {
         cwd = app.getCurrentDirectory();
      }

      chooser.setCurrentDirectory(new File(cwd));
      int status = chooser.showOpenDialog((Component)null);
      if (status == 0) {
         svm.pushString(chooser.getSelectedFile().getAbsolutePath());
         svm.push(callback);
         svm.call(1);
      } else if (cancel != null) {
         svm.push(cancel);
         svm.call(0);
      }

   }
}
