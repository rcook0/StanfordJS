package edu.stanford.cs.sjslib.graphics;

public class SJSGRoundRectClass extends SJSGObjectClass {
   public SJSGRoundRectClass() {
      this.defineMethod("new", new GRoundRect_new());
      this.defineMethod("setFilled", new GRoundRect_setFilled());
      this.defineMethod("setFillColor", new GRoundRect_setFillColor());
      this.defineMethod("getFillColor", new GRoundRect_getFillColor());
      this.defineMethod("isFilled", new GRoundRect_isFilled());
      this.defineMethod("setBounds", new GRoundRect_setBounds());
   }
}
