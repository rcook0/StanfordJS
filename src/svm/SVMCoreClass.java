package edu.stanford.cs.svm;

public class SVMCoreClass extends SVMClass {
   public SVMCoreClass() {
      this.defineMethod("FALSE", new Core_FALSE());
      this.defineMethod("TRUE", new Core_TRUE());
      this.defineMethod("NULL", new Core_NULL());
      this.defineMethod("UNDEFINED", new Core_UNDEFINED());
      this.defineMethod("INFINITY", new Core_INFINITY());
      this.defineMethod("NaN", new Core_NaN());
      this.defineMethod("this", new Core_this());
      this.defineMethod("setReceiver", new Core_setReceiver());
      this.defineMethod("select", new Core_select());
      this.defineMethod("assign", new Core_assign());
      this.defineMethod("typeof", new Core_typeof());
   }
}
