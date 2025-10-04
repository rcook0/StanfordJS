package edu.stanford.cs.sjslib.graphics;

public class SJSGOvalClass extends SJSGObjectClass {
   public SJSGOvalClass() {
      this.defineMethod("new", new GOval_new());
      this.defineMethod("setFilled", new GOval_setFilled());
      this.defineMethod("setFillColor", new GOval_setFillColor());
      this.defineMethod("getFillColor", new GOval_getFillColor());
      this.defineMethod("isFilled", new GOval_isFilled());
      this.defineMethod("setBounds", new GOval_setBounds());
   }
}
