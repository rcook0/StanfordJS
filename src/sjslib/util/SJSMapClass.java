package edu.stanford.cs.sjslib.util;

import edu.stanford.cs.svm.SVMClass;

public class SJSMapClass extends SVMClass {
   public SJSMapClass() {
      this.defineMethod("new", new Map_new());
      this.defineMethod("keyArray", new Map_keyArray());
      this.defineMethod("size", new Map_size());
      this.defineMethod("isEmpty", new Map_isEmpty());
      this.defineMethod("clear", new Map_clear());
      this.defineMethod("put", new Map_put());
      this.defineMethod("get", new Map_get());
      this.defineMethod("containsKey", new Map_containsKey());
      this.defineMethod("remove", new Map_remove());
   }
}
