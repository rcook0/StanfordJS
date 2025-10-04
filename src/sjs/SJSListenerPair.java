package edu.stanford.cs.sjs;

import edu.stanford.cs.exp.Value;

class SJSListenerPair {
   String eventType;
   Value callback;

   public SJSListenerPair(String eventType, Value callback) {
      this.eventType = eventType;
      this.callback = callback;
   }
}
