package edu.stanford.cs.sjslib.graphics;

public class SJSGCompoundClass extends SJSGObjectClass {
   public SJSGCompoundClass() {
      this.defineMethod("new", new GCompound_new());
      this.defineMethod("add", new GCompound_add());
      this.defineMethod("remove", new GCompound_remove());
   }
}
