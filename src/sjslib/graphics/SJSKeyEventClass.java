package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.sjslib.core.SJSEventClass;

public class SJSKeyEventClass extends SJSEventClass {
   public static final int KEY_TYPED = 201;
   public static final int KEY_PRESSED = 202;
   public static final int KEY_RELEASED = 203;

   public SJSKeyEventClass() {
      this.defineMethod("getEventType", new KeyEvent_getEventType());
      this.defineMethod("getID", new KeyEvent_getID());
      this.defineMethod("getSource", new KeyEvent_getSource());
      this.defineMethod("getKeyChar", new KeyEvent_getKeyChar());
      this.defineMethod("getKeyCode", new KeyEvent_getKeyCode());
      this.defineMethod("TYPE", new KeyEvent_TYPE());
      this.defineMethod("KEY_TYPED", new KeyEvent_KEY_TYPED());
      this.defineMethod("KEY_PRESSED", new KeyEvent_KEY_PRESSED());
      this.defineMethod("KEY_RELEASED", new KeyEvent_KEY_RELEASED());
   }
}
