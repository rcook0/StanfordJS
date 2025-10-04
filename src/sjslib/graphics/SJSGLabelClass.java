package edu.stanford.cs.sjslib.graphics;

public class SJSGLabelClass extends SJSGObjectClass {
   public SJSGLabelClass() {
      this.defineMethod("new", new GLabel_new());
      this.defineMethod("getAscent", new GLabel_getAscent());
      this.defineMethod("getDescent", new GLabel_getDescent());
      this.defineMethod("getLabel", new GLabel_getLabel());
      this.defineMethod("setFont", new GLabel_setFont());
      this.defineMethod("setLabel", new GLabel_setLabel());
   }
}
