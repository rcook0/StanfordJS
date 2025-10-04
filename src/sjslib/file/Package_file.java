package edu.stanford.cs.sjslib.file;

import edu.stanford.cs.svm.SVM;
import edu.stanford.cs.svm.SVMPackage;

public class Package_file extends SVMPackage {
   public void defineClasses(SVM svm) {
      this.defineClass(svm, "File", new SJSFileClass());
   }
}
