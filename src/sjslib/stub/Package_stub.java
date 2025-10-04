package edu.stanford.cs.sjslib.stub;

import edu.stanford.cs.svm.SVM;
import edu.stanford.cs.svm.SVMPackage;

public class Package_stub extends SVMPackage {
   public void defineClasses(SVM svm) {
      this.defineClass(svm, "Stub", new SJSStubClass());
   }
}
