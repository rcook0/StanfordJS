package edu.stanford.cs.sjslib.core;

import edu.stanford.cs.svm.SVMClass;

public class SJSNumberClass extends SVMClass {
   public SJSNumberClass() {
      this.defineMethod("isFinite", new Number_isFinite());
      this.defineMethod("isNaN", new Number_isNaN());
      this.defineMethod("parseFloat", new Number_parseFloat());
      this.defineMethod("parseInt", new Number_parseInt());
      this.defineMethod("toString", new Number_toString());
   }
}
