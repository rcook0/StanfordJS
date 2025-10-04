package edu.stanford.cs.sjslib.core;

import edu.stanford.cs.svm.SVMClass;

public abstract class SJSEventClass extends SVMClass {
   public static final int ACTION_EVENT = 100;
   public static final int KEY_EVENT = 200;
   public static final int MOUSE_EVENT = 300;

   public SJSEventClass() {
      this.defineMethod("ACTION_EVENT", new Event_ACTION_EVENT());
      this.defineMethod("KEY_EVENT", new Event_KEY_EVENT());
      this.defineMethod("MOUSE_EVENT", new Event_MOUSE_EVENT());
   }
}
