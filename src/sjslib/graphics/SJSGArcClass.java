package edu.stanford.cs.sjslib.graphics;

public class SJSGArcClass extends SJSGObjectClass {
   public SJSGArcClass() {
      this.defineMethod("new", new GArc_new());
      this.defineMethod("setFilled", new GArc_setFilled());
      this.defineMethod("setFillColor", new GArc_setFillColor());
      this.defineMethod("getFillColor", new GArc_getFillColor());
      this.defineMethod("isFilled", new GArc_isFilled());
      this.defineMethod("setStartAngle", new GArc_setStartAngle());
      this.defineMethod("setSweepAngle", new GArc_setSweepAngle());
      this.defineMethod("getStartAngle", new GArc_getStartAngle());
      this.defineMethod("getSweepAngle", new GArc_getSweepAngle());
      this.defineMethod("getStartPoint", new GArc_getStartPoint());
      this.defineMethod("getEndPoint", new GArc_getEndPoint());
   }
}
