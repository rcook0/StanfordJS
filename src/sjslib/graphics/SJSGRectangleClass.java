package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.svm.SVMClass;

public class SJSGRectangleClass extends SVMClass {
   public SJSGRectangleClass() {
      this.defineMethod("new", new GRectangle_new());
      this.defineMethod("getX", new GRectangle_getX());
      this.defineMethod("getY", new GRectangle_getY());
      this.defineMethod("getWidth", new GRectangle_getWidth());
      this.defineMethod("getHeight", new GRectangle_getHeight());
   }
}
