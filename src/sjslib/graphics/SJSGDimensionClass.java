package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.svm.SVMClass;

public class SJSGDimensionClass extends SVMClass {
   public SJSGDimensionClass() {
      this.defineMethod("new", new GDimension_new());
      this.defineMethod("getWidth", new GDimension_getWidth());
      this.defineMethod("getHeight", new GDimension_getHeight());
   }
}
