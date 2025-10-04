package edu.stanford.cs.svm;

public class SVMGlobalClass extends SVMClass {
   public SVMGlobalClass() {
      this.defineMethod("get", new Global_get());
      this.defineMethod("set", new Global_set());
      this.defineMethod("isDefined", new Global_isDefined());
   }
}
