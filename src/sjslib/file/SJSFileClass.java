package edu.stanford.cs.sjslib.file;

import edu.stanford.cs.sjs.SJS;
import edu.stanford.cs.sjs.SJSVM;
import edu.stanford.cs.svm.SVM;
import edu.stanford.cs.svm.SVMClass;

public class SJSFileClass extends SVMClass {
   public SJSFileClass() {
      this.defineMethod("chooseFile", new File_chooseFile());
      this.defineMethod("read", new File_read());
      this.defineMethod("readLines", new File_readLines());
   }

   protected static String expandPathname(SVM svm, String pathname) {
      String cwd = ".";
      SJS app = ((SJSVM)svm).getApplication();
      if (app != null) {
         cwd = app.getCurrentDirectory();
      }

      if (pathname.startsWith("/")) {
         return pathname;
      } else {
         if (!cwd.endsWith("/")) {
            cwd = cwd + "/";
         }

         return cwd + pathname;
      }
   }
}
