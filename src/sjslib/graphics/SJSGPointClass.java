package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.svm.SVMClass;

public class SJSGPointClass extends SVMClass {
   public SJSGPointClass() {
      this.defineMethod("new", new GPoint_new());
      this.defineMethod("getX", new GPoint_getX());
      this.defineMethod("getY", new GPoint_getY());
   }
}
