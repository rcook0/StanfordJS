package edu.stanford.cs.sjslib.graphics;

public class SJSGPolygonClass extends SJSGObjectClass {
   public SJSGPolygonClass() {
      this.defineMethod("new", new GPolygon_new());
      this.defineMethod("addVertex", new GPolygon_addVertex());
      this.defineMethod("addEdge", new GPolygon_addEdge());
      this.defineMethod("addPolarEdge", new GPolygon_addPolarEdge());
      this.defineMethod("setFilled", new GPolygon_setFilled());
      this.defineMethod("setFillColor", new GPolygon_setFillColor());
      this.defineMethod("getFillColor", new GPolygon_getFillColor());
      this.defineMethod("isFilled", new GPolygon_isFilled());
   }
}
