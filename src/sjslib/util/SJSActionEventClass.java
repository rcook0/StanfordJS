package edu.stanford.cs.sjslib.util;

import edu.stanford.cs.sjslib.core.SJSEventClass;

public class SJSActionEventClass extends SJSEventClass {
   public static final int ACTION_PERFORMED = 101;

   public SJSActionEventClass() {
      this.defineMethod("getEventType", new ActionEvent_getEventType());
      this.defineMethod("getID", new ActionEvent_getID());
      this.defineMethod("getSource", new ActionEvent_getSource());
      this.defineMethod("getActionCommand", new ActionEvent_getActionCommand());
      this.defineMethod("TYPE", new ActionEvent_TYPE());
      this.defineMethod("ACTION_PERFORMED", new ActionEvent_ACTION_PERFORMED());
   }
}
