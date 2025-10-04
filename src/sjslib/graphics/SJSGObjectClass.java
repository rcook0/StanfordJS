package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.svm.SVMClass;

public class SJSGObjectClass extends SVMClass {
   public SJSGObjectClass() {
      this.defineMethod("getBounds", new GObject_getBounds());
      this.defineMethod("setLocation", new GObject_setLocation());
      this.defineMethod("getLocation", new GObject_getLocation());
      this.defineMethod("getX", new GObject_getX());
      this.defineMethod("getY", new GObject_getY());
      this.defineMethod("move", new GObject_move());
      this.defineMethod("movePolar", new GObject_movePolar());
      this.defineMethod("getSize", new GObject_getSize());
      this.defineMethod("getWidth", new GObject_getWidth());
      this.defineMethod("getHeight", new GObject_getHeight());
      this.defineMethod("contains", new GObject_contains());
      this.defineMethod("setColor", new GObject_setColor());
      this.defineMethod("getColor", new GObject_getColor());
      this.defineMethod("getJavaColor", new GObject_getJavaColor());
      this.defineMethod("setLineWidth", new GObject_setLineWidth());
      this.defineMethod("getLineWidth", new GObject_getLineWidth());
      this.defineMethod("rotate", new GObject_rotate());
      this.defineMethod("scale", new GObject_scale());
      this.defineMethod("shear", new GObject_shear());
      this.defineMethod("translate", new GObject_translate());
      this.defineMethod("setVisible", new GObject_setVisible());
      this.defineMethod("isVisible", new GObject_isVisible());
      this.defineMethod("sendBackward", new GObject_sendBackward());
      this.defineMethod("sendForward", new GObject_sendForward());
      this.defineMethod("sendToBack", new GObject_sendToBack());
      this.defineMethod("sendToFront", new GObject_sendToFront());
   }
}
