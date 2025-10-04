package edu.stanford.cs.sjslib.stub;

import edu.stanford.cs.svm.SVMClass;

public class SJSStubClass extends SVMClass {
   public SJSStubClass() {
      this.defineMethod("new", new Stub_new());
   }
}
