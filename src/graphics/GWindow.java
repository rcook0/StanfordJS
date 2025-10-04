package edu.stanford.cs.graphics;

import java.awt.BorderLayout;
import java.awt.Font;
import java.util.Iterator;
import javax.swing.JFrame;

public class GWindow extends JFrame implements Iterable<GObject> {
   public static final int DO_NOTHING_ON_CLOSE = 0;
   public static final int HIDE_ON_CLOSE = 1;
   public static final int DISPOSE_ON_CLOSE = 2;
   public static final int EXIT_ON_CLOSE = 3;
   private GCanvas gc;

   public GWindow() {
      this(new GCanvas());
   }

   public GWindow(double width, double height) {
      this(new GCanvas(width, height));
   }

   public GWindow(GCanvas gc) {
      super("Graphics Window");
      this.loadFontCache();
      this.gc = gc;
      this.setDefaultCloseOperation(3);
      this.setLayout(new BorderLayout());
      this.add(gc, "Center");
      this.pack();
      this.setVisible(true);
   }

   public GCanvas getGCanvas() {
      return this.gc;
   }

   public int getWidth() {
      return super.getWidth();
   }

   public int getHeight() {
      return super.getHeight();
   }

   public double getCanvasWidth() {
      return (double)this.gc.getWidth();
   }

   public double getCanvasHeight() {
      return (double)this.gc.getHeight();
   }

   public void add(GObject gobj) {
      this.gc.add(gobj);
   }

   public final void add(GObject gobj, double x, double y) {
      this.gc.add(gobj, x, y);
   }

   public final void add(GObject gobj, GPoint pt) {
      this.gc.add(gobj, pt);
   }

   public void remove(GObject gobj) {
      this.gc.remove(gobj);
   }

   public void clear() {
      this.removeAll();
   }

   public void removeAll() {
      this.gc.removeAll();
   }

   public int getElementCount() {
      return this.gc.getElementCount();
   }

   public GObject getElement(int index) {
      return this.gc.getElement(index);
   }

   public GObject getElementAt(double x, double y) {
      return this.gc.getElementAt(x, y);
   }

   public final GObject getElementAt(GPoint pt) {
      return this.gc.getElementAt(pt);
   }

   public Iterator<GObject> iterator() {
      return this.gc.iterator();
   }

   public Iterator<GObject> iterator(int direction) {
      return this.gc.iterator(direction);
   }

   public void setAutoRepaintFlag(boolean state) {
      this.gc.setAutoRepaintFlag(state);
   }

   public boolean getAutoRepaintFlag() {
      return this.gc.getAutoRepaintFlag();
   }

   public void install(String id) {
      this.setVisible(true);
   }

   private void loadFontCache() {
      this.getFontMetrics(new Font("Serif", 0, 10));
   }
}
