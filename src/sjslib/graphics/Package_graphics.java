package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.svm.SVM;
import edu.stanford.cs.svm.SVMPackage;

public class Package_graphics extends SVMPackage {
   private String[] DEPENDENCIES = new String[]{"jslib", "edu.stanford.cs.graphics", "java.awt"};
   private String[] WRAPPER = new String[]{"", "/* Wrapper to read in the graphics package */", "", "var Color = java_awt.Color;", "var GArc = edu_stanford_cs_graphics.GArc;", "var GCompound = edu_stanford_cs_graphics.GCompound;", "var GDimension = edu_stanford_cs_graphics.GDimension;", "var GImage = edu_stanford_cs_graphics.GImage;", "var GLabel = edu_stanford_cs_graphics.GLabel;", "var GLine = edu_stanford_cs_graphics.GLine;", "var GObject = edu_stanford_cs_graphics.GObject;", "var GOval = edu_stanford_cs_graphics.GOval;", "var GPoint = edu_stanford_cs_graphics.GPoint;", "var GPolygon = edu_stanford_cs_graphics.GPolygon;", "var GRect = edu_stanford_cs_graphics.GRect;", "var GRectangle = edu_stanford_cs_graphics.GRectangle;", "var GRoundRect = edu_stanford_cs_graphics.GRoundRect;", "var GTransform = edu_stanford_cs_graphics.GTransform;", "var GWindow = edu_stanford_cs_graphics.GWindow;", "var KeyEvent = java_awt.KeyEvent;", "var MouseEvent = java_awt.MouseEvent;", "", "/* Patch the GWindow class to support installing the window */", "", "GWindow.prototype.init = function() {", "   pgm.add(this, \"canvas\");", "}", "", "GWindow.prototype.addEventListener = function(type, listener) {", "   if (GWindow.MOUSE_EVENT_TYPES[type]) {", "      jslib.addListener(this.element, GWindow.MOUSE_EVENT_TYPES[type],", "                        function(e) {", "                           listener(new MouseEvent(e, this));", "                        });", "   }", "};", "", "GWindow.MOUSE_EVENT_TYPES = {", "", "   click: \"click\",", "   dblclick: \"dblclick\",", "   mouseClicked: \"click\",", "   mousedown: \"mousedown\",", "   mousePressed: \"mousedown\",", "   mouseup: \"mouseup\",", "   mouseReleased: \"mouseup\",", "   mousemove: \"mousemove\",", "   mouseMoved: \"mousemove\",", "   mousedrag: \"mousedrag\",", "   mouseDragged: \"mousedrag\"", "};"};

   public void defineClasses(SVM svm) {
      this.defineClass(svm, "Color", new SJSColorClass());
      this.defineClass(svm, "GArc", new SJSGArcClass());
      this.defineClass(svm, "GCompound", new SJSGCompoundClass());
      this.defineClass(svm, "GDimension", new SJSGDimensionClass());
      this.defineClass(svm, "GImage", new SJSGImageClass());
      this.defineClass(svm, "GLabel", new SJSGLabelClass());
      this.defineClass(svm, "GLine", new SJSGLineClass());
      this.defineClass(svm, "GObject", new SJSGObjectClass());
      this.defineClass(svm, "GOval", new SJSGOvalClass());
      this.defineClass(svm, "GPoint", new SJSGPointClass());
      this.defineClass(svm, "GPolygon", new SJSGPolygonClass());
      this.defineClass(svm, "GRect", new SJSGRectClass());
      this.defineClass(svm, "GRectangle", new SJSGRectangleClass());
      this.defineClass(svm, "GRoundRect", new SJSGRoundRectClass());
      this.defineClass(svm, "GTransform", new SJSGTransformClass());
      this.defineClass(svm, "GWindow", new SJSGWindowClass());
      this.defineClass(svm, "KeyEvent", new SJSKeyEventClass());
      this.defineClass(svm, "MouseEvent", new SJSMouseEventClass());
   }

   public String[] getDependencies() {
      return this.DEPENDENCIES;
   }

   public String[] getWrapper() {
      return this.WRAPPER;
   }
}
