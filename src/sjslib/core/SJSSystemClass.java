package edu.stanford.cs.sjslib.core;

import edu.stanford.cs.svm.SVMClass;

public class SJSSystemClass extends SVMClass {
   public SJSSystemClass() {
      this.defineMethod("currentTimeMillis", new System_currentTimeMillis());
      this.defineMethod("getSourceFile", new System_getSourceFile());
   }
}
