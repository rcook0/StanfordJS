package edu.stanford.cs.graphics;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.util.Iterator;

public class GCompound extends GObject implements GContainer, Iterable<GObject> {
   private GObjectList contents = new GObjectList(this);
   private transient GObject lastObject;
   private transient GObject dragObject;

   public void add(GObject gobj) {
      this.contents.add(gobj);
      this.repaint();
   }

   public final void add(GObject gobj, double x, double y) {
      gobj.setLocation(x, y);
      this.add(gobj);
   }

   public final void add(GObject gobj, GPoint pt) {
      this.add(gobj, pt.getX(), pt.getY());
   }

   public void remove(GObject gobj) {
      this.contents.remove(gobj);
      this.repaint();
   }

   public void removeAll() {
      this.contents.removeAll();
      this.repaint();
   }

   public int getElementCount() {
      return this.contents.getElementCount();
   }

   public GObject getElement(int index) {
      return this.contents.getElement(index);
   }

   public GObject getElementAt(double x, double y) {
      return this.contents.getElementAt(x, y, false);
   }

   public final GObject getElementAt(GPoint pt) {
      return this.getElementAt(pt.getX(), pt.getY());
   }

   public Iterator<GObject> iterator() {
      return new GIterator(this, 0);
   }

   public Iterator<GObject> iterator(int direction) {
      return new GIterator(this, direction);
   }

   public Object clone() {
      try {
         GCompound clone = (GCompound)super.clone();
         clone.contents = new GObjectList(clone, this.contents);
         return clone;
      } catch (Exception var2) {
         throw new RuntimeException("Impossible exception");
      }
   }

   public final GPoint getCanvasPoint(GPoint localPoint) {
      return this.getCanvasPoint(localPoint.getX(), localPoint.getY());
   }

   public GPoint getCanvasPoint(double x, double y) {
      GCompound comp;
      for(Object c = this; c instanceof GCompound; c = comp.getParent()) {
         comp = (GCompound)c;
         x += comp.getX();
         y += comp.getY();
      }

      return new GPoint(x, y);
   }

   public final GPoint getLocalPoint(GPoint canvasPoint) {
      return this.getLocalPoint(canvasPoint.getX(), canvasPoint.getY());
   }

   public GPoint getLocalPoint(double x, double y) {
      GCompound comp;
      for(Object c = this; c instanceof GCompound; c = comp.getParent()) {
         comp = (GCompound)c;
         x -= comp.getX();
         y -= comp.getY();
      }

      return new GPoint(x, y);
   }

   protected GRectangle localBounds(GTransform ctm) {
      GRectangle bb = new GRectangle();
      synchronized(this.contents) {
         int n = this.contents.getElementCount();

         for(int i = 0; i < n; ++i) {
            GObject obj = this.contents.getElement(i);
            GTransform t = new GTransform(ctm);
            t.translate(obj.getX(), obj.getY());
            t.concatenate(obj.getCTM());
            GRectangle r = obj.localBounds(t);
            if (i == 0) {
               bb = r;
            } else {
               bb.add(r);
            }
         }

         return bb;
      }
   }

   protected boolean localContains(double x, double y) {
      synchronized(this.contents) {
         int n = this.contents.getElementCount();

         for(int i = 0; i < n; ++i) {
            if (this.contents.getElement(i).contains(x, y)) {
               return true;
            }
         }

         return false;
      }
   }

   protected void paint2d(Graphics2D g) {
      this.contents.mapPaint(g);
   }

   protected void sendToFront(GObject gobj) {
      this.contents.sendToFront(gobj);
      this.repaint();
   }

   protected void sendToBack(GObject gobj) {
      this.contents.sendToBack(gobj);
      this.repaint();
   }

   protected void sendForward(GObject gobj) {
      this.contents.sendForward(gobj);
      this.repaint();
   }

   protected void sendBackward(GObject gobj) {
      this.contents.sendBackward(gobj);
      this.repaint();
   }

   protected void fireMouseListeners(MouseEvent e) {
      if (super.areMouseListenersEnabled()) {
         super.fireMouseListeners(e);
      } else {
         GPoint pt = new GPoint((double)e.getX() - this.getX(), (double)e.getY() - this.getY());
         GObject gobj = this.getElementAt(pt);
         MouseEvent newEvent = null;
         if (gobj != this.lastObject) {
            if (this.lastObject != null) {
               newEvent = GCanvas.createMouseEvent(this.lastObject, 505, e);
               this.lastObject.fireMouseListeners(newEvent);
            }

            if (gobj != null) {
               newEvent = GCanvas.createMouseEvent(gobj, 504, e);
               gobj.fireMouseListeners(newEvent);
            }
         }

         this.lastObject = gobj;
         if (this.dragObject != null) {
            gobj = this.dragObject;
         }

         if (gobj != null) {
            int id = e.getID();
            if (id != 505 && id != 504) {
               if (id == 501) {
                  this.dragObject = gobj;
               } else if (id == 502) {
                  this.dragObject = null;
               }

               newEvent = GCanvas.createMouseEvent(gobj, id, e);
               gobj.fireMouseListeners(newEvent);
            }
         }

         if (newEvent != null && newEvent.isConsumed()) {
            e.consume();
         }

      }
   }

   protected boolean areMouseListenersEnabled() {
      return super.areMouseListenersEnabled() ? true : this.contents.areMouseListenersEnabled();
   }
}
