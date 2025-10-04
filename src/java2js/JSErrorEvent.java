package edu.stanford.cs.java2js;

import java.awt.event.ActionEvent;

public class JSErrorEvent extends ActionEvent {
   private Exception exception;

   public JSErrorEvent(Object source, String msg) {
      this(source, msg, (Exception)null);
   }

   public JSErrorEvent(Object source, String msg, Exception ex) {
      super(source, 1001, msg);
      this.exception = ex;
   }

   public Exception getException() {
      return this.exception;
   }
}
