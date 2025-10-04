package edu.stanford.cs.graphics;

import java.awt.Component;
import java.awt.event.MouseEvent;

class GMouseEvent extends MouseEvent {
   private Object effectiveSource;

   public GMouseEvent(Object gobj, int eventID, MouseEvent e) {
      super(e.getComponent(), eventID, e.getWhen(), e.getModifiers(), e.getX(), e.getY(), e.getClickCount(), e.isPopupTrigger());
      this.effectiveSource = gobj;
   }

   public Object getSource() {
      return this.effectiveSource;
   }

   public Component getComponent() {
      return (Component)super.getSource();
   }

   public String toString() {
      return this.getClass().getName() + "[" + this.paramString() + "] on " + this.getSource();
   }
}
