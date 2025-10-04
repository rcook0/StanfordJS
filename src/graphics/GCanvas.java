package edu.stanford.cs.graphics;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.LayoutManager;
import java.awt.event.MouseEvent;
import java.util.Iterator;
import javax.swing.JComponent;

public class GCanvas extends JComponent implements GContainer, Iterable<GObject> {
   public static final int DEFAULT_WIDTH = 500;
   public static final int DEFAULT_HEIGHT = 300;
   private GCanvasListener gCanvasListener;
   private GObject lastObject;
   private GObject dragObject;
   private GObjectList contents;
   private boolean autoRepaint;
   private boolean nativeArcFlag;

   public GCanvas() {
      this(500.0D, 300.0D);
   }

   public GCanvas(double width, double height) {
      this.contents = new GObjectList(this);
      this.setBackground(Color.WHITE);
      this.setForeground(Color.BLACK);
      this.setOpaque(true);
      this.setAutoRepaintFlag(true);
      this.setNativeArcFlag(false);
      this.setLayout((LayoutManager)null);
      this.gCanvasListener = new GCanvasListener(this);
      this.addMouseListener(this.gCanvasListener);
      this.addMouseMotionListener(this.gCanvasListener);
      Dimension size = new Dimension(GMath.round(width), GMath.round(height));
      this.setPreferredSize(size);
      this.setSize(size);
   }

   public void clear() {
      this.removeAll();
   }

   public void removeAll() {
      this.contents.removeAll();
      this.conditionalRepaint();
   }

   public void add(GObject gobj) {
      this.contents.add(gobj);
      this.conditionalRepaint();
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
      this.conditionalRepaint();
   }

   public Component add(Component comp) {
      super.add(comp);
      Dimension size = comp.getSize();
      if (size.width == 0 || size.height == 0) {
         Dimension preferredSize = comp.getPreferredSize();
         if (size.width == 0) {
            size.width = preferredSize.width;
         }

         if (size.height == 0) {
            size.height = preferredSize.height;
         }

         comp.setSize(size);
      }

      return comp;
   }

   public final void add(Component comp, double x, double y) {
      comp.setLocation(GMath.round(x), GMath.round(y));
      this.add(comp);
   }

   public final void add(Component comp, GPoint pt) {
      this.add(comp, pt.getX(), pt.getY());
   }

   public void remove(Component comp) {
      super.remove(comp);
      this.conditionalRepaint();
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

   public final void setPreferredSize(double width, double height) {
      this.setPreferredSize(new Dimension(GMath.round(width), GMath.round(height)));
   }

   public Iterator<GObject> iterator() {
      return new GIterator(this, 0);
   }

   public Iterator<GObject> iterator(int direction) {
      return new GIterator(this, direction);
   }

   public void paint(Graphics g) {
      if (this.isOpaque()) {
         g.setColor(this.getBackground());
         g.fillRect(0, 0, this.getWidth(), this.getHeight());
         g.setColor(this.getForeground());
      }

      super.paint(g);
   }

   public void paintComponent(Graphics g) {
      this.contents.mapPaint(g);
      super.paintComponent(g);
   }

   public void setAutoRepaintFlag(boolean state) {
      this.autoRepaint = state;
   }

   public boolean getAutoRepaintFlag() {
      return this.autoRepaint;
   }

   public void setNativeArcFlag(boolean state) {
      this.nativeArcFlag = state;
   }

   public boolean getNativeArcFlag() {
      return this.nativeArcFlag;
   }

   protected void sendToFront(GObject gobj) {
      this.contents.sendToFront(gobj);
      this.conditionalRepaint();
   }

   protected void sendToBack(GObject gobj) {
      this.contents.sendToBack(gobj);
      this.conditionalRepaint();
   }

   protected void sendForward(GObject gobj) {
      this.contents.sendForward(gobj);
      this.conditionalRepaint();
   }

   protected void sendBackward(GObject gobj) {
      this.contents.sendBackward(gobj);
      this.conditionalRepaint();
   }

   protected void dispatchMouseEvent(MouseEvent e) {
      GObject gobj = this.contents.getElementAt((double)e.getX(), (double)e.getY(), true);
      MouseEvent newEvent = null;
      if (gobj != this.lastObject) {
         if (this.lastObject != null) {
            newEvent = new GMouseEvent(this.lastObject, 505, e);
            this.lastObject.fireMouseListeners(newEvent);
         }

         if (gobj != null) {
            newEvent = new GMouseEvent(gobj, 504, e);
            gobj.fireMouseListeners(newEvent);
         }
      }

      this.lastObject = gobj;
      if (this.dragObject != null) {
         gobj = this.dragObject;
      }

      if (gobj != null) {
         int id = e.getID();
         if (id != 505 && id != 504 && (id != 506 || this.dragObject != null)) {
            if (id == 501) {
               this.dragObject = gobj;
            } else if (id == 502) {
               this.dragObject = null;
            }

            newEvent = new GMouseEvent(gobj, id, e);
            gobj.fireMouseListeners(newEvent);
         }
      }

      if (newEvent != null && newEvent.isConsumed()) {
         e.consume();
      }

   }

   protected void conditionalRepaint() {
      if (this.autoRepaint) {
         this.repaint();
      }

   }

   protected void updateEnabledList() {
      this.contents.updateEnabledList();
   }

   static MouseEvent createMouseEvent(Object gobj, int eventID, MouseEvent e) {
      return new GMouseEvent(gobj, eventID, e);
   }
}
