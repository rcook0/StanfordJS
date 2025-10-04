package edu.stanford.cs.graphics;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Iterator;
import javax.swing.JButton;

public class GProgram implements MouseListener, MouseMotionListener, KeyListener, ActionListener, Iterable<GObject> {
   private GProgramListener eventListener;
   private GWindow gw = new GWindow();

   public GProgram() {
      this.gw.setAutoRepaintFlag(false);
      this.eventListener = new GProgramListener(this.gw);
      this.gw.addComponentListener(this.eventListener);
      this.gw.getGCanvas().addMouseListener(this.eventListener);
   }

   public void setSize(double width, double height) {
      Dimension size = new Dimension(GMath.round(width), GMath.round(height));
      GCanvas gc = this.gw.getGCanvas();
      gc.setSize(size);
      gc.setPreferredSize(size);
      this.gw.pack();
   }

   public GWindow getGWindow() {
      return this.gw;
   }

   public GCanvas getGCanvas() {
      return this.gw.getGCanvas();
   }

   public double getWidth() {
      return this.gw.getCanvasWidth();
   }

   public double getHeight() {
      return this.gw.getCanvasHeight();
   }

   public void add(GObject gobj) {
      this.gw.add(gobj);
   }

   public void add(GObject gobj, double x, double y) {
      this.gw.add(gobj, x, y);
   }

   public void add(GObject gobj, GPoint pt) {
      this.gw.add(gobj, pt);
   }

   public void remove(GObject gobj) {
      this.gw.remove(gobj);
   }

   public void clear() {
      this.removeAll();
   }

   public void removeAll() {
      this.gw.removeAll();
   }

   public int getElementCount() {
      return this.gw.getElementCount();
   }

   public GObject getElement(int index) {
      return this.gw.getElement(index);
   }

   public GObject getElementAt(double x, double y) {
      return this.gw.getElementAt(x, y);
   }

   public GObject getElementAt(GPoint pt) {
      return this.gw.getElementAt(pt);
   }

   public Iterator<GObject> iterator() {
      return this.gw.iterator();
   }

   public Iterator<GObject> iterator(int direction) {
      return this.gw.iterator(direction);
   }

   public void repaint() {
      this.gw.repaint();
   }

   public void waitForClick() {
      this.repaint();
      this.eventListener.waitForClick();
   }

   public void pause(double milliseconds) {
      this.repaint();

      try {
         int millis = (int)milliseconds;
         int nanos = (int)Math.round((milliseconds - (double)millis) * 1000000.0D);
         Thread.sleep((long)millis, nanos);
      } catch (InterruptedException var5) {
      }

   }

   public void addActionListeners() {
      this.addActionListeners(this);
   }

   public void addActionListeners(ActionListener listener) {
      this.addActionListeners(this.gw, listener);
   }

   public void addMouseListeners() {
      GCanvas gc = this.gw.getGCanvas();
      gc.addMouseListener(this);
      gc.addMouseMotionListener(this);
   }

   public void addKeyListeners() {
      this.gw.getGCanvas().addKeyListener(this);
   }

   public void mouseClicked(MouseEvent e) {
   }

   public void mousePressed(MouseEvent e) {
   }

   public void mouseReleased(MouseEvent e) {
   }

   public void mouseEntered(MouseEvent e) {
   }

   public void mouseExited(MouseEvent e) {
   }

   public void mouseMoved(MouseEvent e) {
   }

   public void mouseDragged(MouseEvent e) {
   }

   public void keyTyped(KeyEvent e) {
   }

   public void keyPressed(KeyEvent e) {
   }

   public void keyReleased(KeyEvent e) {
   }

   public void actionPerformed(ActionEvent e) {
   }

   private void addActionListeners(Component comp, ActionListener listener) {
      if (comp instanceof JButton) {
         JButton button = (JButton)comp;
         if (button.getActionListeners().length == 0) {
            button.addActionListener(listener);
         }
      } else if (comp instanceof Container) {
         Container container = (Container)comp;
         int nComponents = container.getComponentCount();

         for(int i = 0; i < nComponents; ++i) {
            this.addActionListeners(container.getComponent(i), listener);
         }
      }

   }
}
