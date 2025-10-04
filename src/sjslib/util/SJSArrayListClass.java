package edu.stanford.cs.sjslib.util;

import edu.stanford.cs.svm.SVMClass;

public class SJSArrayListClass extends SVMClass {
   public SJSArrayListClass() {
      this.defineMethod("new", new ArrayList_new());
      this.defineMethod("size", new ArrayList_size());
      this.defineMethod("isEmpty", new ArrayList_isEmpty());
      this.defineMethod("clear", new ArrayList_clear());
      this.defineMethod("get", new ArrayList_get());
      this.defineMethod("set", new ArrayList_set());
      this.defineMethod("add", new ArrayList_add());
      this.defineMethod("insert", new ArrayList_insert());
      this.defineMethod("remove", new ArrayList_remove());
   }
}
