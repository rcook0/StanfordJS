package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.svm.SVMClass;

public class SJSGWindowClass extends SVMClass {
   public SJSGWindowClass() {
      this.defineMethod("new", new GWindow_new());
      this.defineMethod("add", new GWindow_add());
      this.defineMethod("clear", new GWindow_clear());
      this.defineMethod("remove", new GWindow_remove());
      this.defineMethod("removeAll", new GWindow_removeAll());
      this.defineMethod("repaint", new GWindow_repaint());
      this.defineMethod("getWidth", new GWindow_getWidth());
      this.defineMethod("getHeight", new GWindow_getHeight());
      this.defineMethod("getCanvasWidth", new GWindow_getCanvasWidth());
      this.defineMethod("getCanvasHeight", new GWindow_getCanvasHeight());
      this.defineMethod("setBackground", new GWindow_setBackground());
      this.defineMethod("getElementAt", new GWindow_getElementAt());
      this.defineMethod("waitForClick", new GWindow_waitForClick());
      this.defineMethod("waitForEvent", new GWindow_waitForEvent());
      this.defineMethod("requestFocus", new GWindow_requestFocus());
      this.defineMethod("addEventListener", new GWindow_addEventListener());
   }
}
