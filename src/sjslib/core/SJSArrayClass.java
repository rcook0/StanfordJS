package edu.stanford.cs.sjslib.core;

import edu.stanford.cs.svm.SVMClass;

public class SJSArrayClass extends SVMClass {
   public SJSArrayClass() {
      this.defineMethod("new", new Array_new());
      this.defineMethod("create", new Array_create());
      this.defineMethod("concat", new Array_concat());
      this.defineMethod("indexOf", new Array_indexOf());
      this.defineMethod("join", new Array_join());
      this.defineMethod("lastIndexOf", new Array_lastIndexOf());
      this.defineMethod("pop", new Array_pop());
      this.defineMethod("push", new Array_push());
      this.defineMethod("reverse", new Array_reverse());
      this.defineMethod("shift", new Array_shift());
      this.defineMethod("size", new Array_size());
      this.defineMethod("slice", new Array_slice());
      this.defineMethod("sort", new Array_sort());
      this.defineMethod("splice", new Array_splice());
      this.defineMethod("unshift", new Array_unshift());
   }
}
