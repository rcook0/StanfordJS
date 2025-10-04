package edu.stanford.cs.sjslib.graphics;

public class SJSGRectClass extends SJSGObjectClass {
   public SJSGRectClass() {
      this.defineMethod("new", new GRect_new());
      this.defineMethod("setFilled", new GRect_setFilled());
      this.defineMethod("setFillColor", new GRect_setFillColor());
      this.defineMethod("getFillColor", new GRect_getFillColor());
      this.defineMethod("isFilled", new GRect_isFilled());
      this.defineMethod("setBounds", new GRect_setBounds());
   }
}
