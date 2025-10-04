package edu.stanford.cs.sjslib.core;

import edu.stanford.cs.svm.SVMClass;

public class SJSObjectClass extends SVMClass {
   public SJSObjectClass() {
      this.defineMethod("new", new Object_new());
      this.defineMethod("create", new Object_create());
      this.defineMethod("keyArray", new Object_keyArray());
   }
}
