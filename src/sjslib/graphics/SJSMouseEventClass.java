package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.sjslib.core.SJSEventClass;

public class SJSMouseEventClass extends SJSEventClass {
   public static final int MOUSE_CLICKED = 301;
   public static final int MOUSE_DRAGGED = 302;
   public static final int MOUSE_MOVED = 303;
   public static final int MOUSE_PRESSED = 304;
   public static final int MOUSE_RELEASED = 305;

   public SJSMouseEventClass() {
      this.defineMethod("getEventType", new MouseEvent_getEventType());
      this.defineMethod("getID", new MouseEvent_getID());
      this.defineMethod("getSource", new MouseEvent_getSource());
      this.defineMethod("getX", new MouseEvent_getX());
      this.defineMethod("getY", new MouseEvent_getY());
      this.defineMethod("TYPE", new MouseEvent_TYPE());
      this.defineMethod("MOUSE_CLICKED", new MouseEvent_MOUSE_CLICKED());
      this.defineMethod("MOUSE_DRAGGED", new MouseEvent_MOUSE_DRAGGED());
      this.defineMethod("MOUSE_MOVED", new MouseEvent_MOUSE_MOVED());
      this.defineMethod("MOUSE_PRESSED", new MouseEvent_MOUSE_PRESSED());
      this.defineMethod("MOUSE_RELEASED", new MouseEvent_MOUSE_RELEASED());
   }
}
