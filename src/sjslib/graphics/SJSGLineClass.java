package edu.stanford.cs.sjslib.graphics;

public class SJSGLineClass extends SJSGObjectClass {
   public SJSGLineClass() {
      this.defineMethod("new", new GLine_new());
      this.defineMethod("getEndPoint", new GLine_getEndPoint());
      this.defineMethod("getStartPoint", new GLine_getStartPoint());
      this.defineMethod("setEndPoint", new GLine_setEndPoint());
      this.defineMethod("setStartPoint", new GLine_setStartPoint());
   }
}
