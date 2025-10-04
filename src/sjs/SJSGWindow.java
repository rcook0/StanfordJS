package edu.stanford.cs.sjs;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.graphics.GCanvas;
import edu.stanford.cs.graphics.GObject;
import edu.stanford.cs.graphics.GWindow;
import edu.stanford.cs.java2js.JSFrame;
import edu.stanford.cs.svm.SVMEventClosure;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class SJSGWindow extends GCanvas implements ActionListener, KeyListener, MouseListener, MouseMotionListener {
   public static final int NORMAL = 0;
   public static final int WAITING_FOR_CLICK = 1;
   public static final int WAITING_FOR_EVENT = 2;
   private ArrayList<SJSListenerPair> jsListeners;
   private GWindow frame;
   private HashMap<GObject, Value> objectMap;
   private JSFrame jsFrame;
   private SJSVM svm;
   private boolean hasKeyListener;
   private boolean hasMouseListener;
   private boolean hasMouseMotionListener;
   private int state;

   public SJSGWindow(SJSVM svm) {
      this.svm = svm;
      this.hasMouseListener = false;
      this.hasMouseMotionListener = false;
      this.state = 0;
      this.frame = null;
      this.jsListeners = new ArrayList();
      this.objectMap = new HashMap();
   }

   public SJSVM getSVM() {
      return this.svm;
   }

   public void setTitle(String title) {
      if (this.frame != null) {
         this.frame.setTitle(title);
      } else if (this.jsFrame != null) {
         this.jsFrame.setTitle(title);
      }

   }

   public void add(Value v) {
      GObject gobj = (GObject)v.getValue();
      this.add(gobj);
      this.objectMap.put(gobj, v);
   }

   public Value getValueForGObject(GObject gobj) {
      return (Value)this.objectMap.get(gobj);
   }

   public void createJSFrame(double width, double height) {
      Dimension size = new Dimension((int)Math.round(width), (int)Math.round(height));
      this.setPreferredSize(size);
      this.jsFrame = new JSFrame(this, "Graphics Window");
   }

   public JSFrame getJSFrame() {
      return this.jsFrame;
   }

   public void createFrame(double width, double height) {
      this.frame = new GWindow(this);
      if (this.svm.getFrame() != null) {
         this.frame.setDefaultCloseOperation(0);
      }

      Dimension size = new Dimension((int)Math.round(width), (int)Math.round(height));
      this.setPreferredSize(size);
      this.frame.addWindowListener(new SJSGWindowListener(this));
      this.frame.pack();
      this.frame.setVisible(true);
   }

   public GWindow getFrame() {
      return this.frame;
   }

   public void close() {
      this.svm.closeGWindow(this);
   }

   public void setState(int state) {
      this.state = state;
   }

   public void enableKeyListener() {
      if (!this.hasKeyListener) {
         this.addKeyListener(this);
         this.hasKeyListener = true;
      }

   }

   public void enableMouseListener() {
      if (!this.hasMouseListener) {
         this.addMouseListener(this);
         this.hasMouseListener = true;
      }

   }

   public void enableMouseMotionListener() {
      if (!this.hasMouseMotionListener) {
         this.addMouseMotionListener(this);
         this.hasMouseMotionListener = true;
      }

   }

   public void addEventListener(String type, Value callback) {
      if (type.equals("click")) {
         this.enableMouseListener();
      } else if (!type.equals("mouseclick") && !type.equals("mouseClicked")) {
         if (type.equals("dblclick")) {
            this.enableMouseListener();
         } else if (type.equals("mousedown")) {
            this.enableMouseListener();
         } else if (type.equals("mousePressed")) {
            type = "mousedown";
            this.enableMouseListener();
         } else if (type.equals("mouseup")) {
            this.enableMouseListener();
         } else if (type.equals("mouseReleased")) {
            type = "mouseup";
            this.enableMouseListener();
         } else if (type.equals("mousemove")) {
            this.enableMouseMotionListener();
         } else if (type.equals("mouseMoved")) {
            type = "mousemove";
            this.enableMouseMotionListener();
         } else if (type.equals("drag")) {
            this.enableMouseMotionListener();
         } else {
            if (!type.equals("mousedrag") && !type.equals("mouseDragged")) {
               throw new RuntimeException("Unimplemented event " + type);
            }

            type = "drag";
            this.enableMouseMotionListener();
         }
      } else {
         type = "click";
         this.enableMouseListener();
      }

      this.jsListeners.add(new SJSListenerPair(type.toLowerCase(), callback));
   }

   public boolean hasActiveListeners() {
      return this.hasMouseListener || this.hasMouseMotionListener;
   }

   public void actionPerformed(ActionEvent e) {
      if (this.state == 2) {
         this.state = 0;
         this.svm.push(Value.createObject(e, "ActionEvent"));
         this.svm.run();
         this.repaint();
      }

   }

   public void keyPressed(KeyEvent e) {
      this.fireKeyEvent(e);
   }

   public void keyReleased(KeyEvent e) {
      this.fireKeyEvent(e);
   }

   public void keyTyped(KeyEvent e) {
      this.fireKeyEvent(e);
   }

   public void mouseClicked(MouseEvent e) {
      if (this.jsListeners.isEmpty()) {
         if (this.state == 1) {
            this.state = 0;
            this.svm.run();
            this.repaint();
         } else {
            this.fireMouseEvent(e);
         }
      } else {
         Iterator var3 = this.jsListeners.iterator();

         while(true) {
            while(var3.hasNext()) {
               SJSListenerPair pair = (SJSListenerPair)var3.next();
               SVMEventClosure closure;
               if (pair.eventType.equals("dblclick") && e.getClickCount() == 2) {
                  closure = new SVMEventClosure(pair.callback);
                  closure.add(Value.createObject(e, "MouseEvent"));
                  this.svm.postEvent(closure);
               } else if (pair.eventType.equals("click")) {
                  closure = new SVMEventClosure(pair.callback);
                  closure.add(Value.createObject(e, "MouseEvent"));
                  this.svm.postEvent(closure);
               }
            }

            return;
         }
      }

   }

   public void mouseEntered(MouseEvent e) {
   }

   public void mouseExited(MouseEvent e) {
   }

   public void mousePressed(MouseEvent e) {
      if (this.jsListeners.isEmpty()) {
         this.fireMouseEvent(e);
      } else {
         Iterator var3 = this.jsListeners.iterator();

         while(var3.hasNext()) {
            SJSListenerPair pair = (SJSListenerPair)var3.next();
            if (pair.eventType.equals("mousedown")) {
               SVMEventClosure closure = new SVMEventClosure(pair.callback);
               closure.add(Value.createObject(e, "MouseEvent"));
               this.svm.postEvent(closure);
            }
         }
      }

   }

   public void mouseReleased(MouseEvent e) {
      if (this.jsListeners.isEmpty()) {
         this.fireMouseEvent(e);
      } else {
         Iterator var3 = this.jsListeners.iterator();

         while(var3.hasNext()) {
            SJSListenerPair pair = (SJSListenerPair)var3.next();
            if (pair.eventType.equals("mouseup")) {
               SVMEventClosure closure = new SVMEventClosure(pair.callback);
               closure.add(Value.createObject(e, "MouseEvent"));
               this.svm.postEvent(closure);
            }
         }
      }

   }

   public void mouseMoved(MouseEvent e) {
      if (this.jsListeners.isEmpty()) {
         this.fireMouseEvent(e);
      } else {
         Iterator var3 = this.jsListeners.iterator();

         while(var3.hasNext()) {
            SJSListenerPair pair = (SJSListenerPair)var3.next();
            if (pair.eventType.equals("mousemove")) {
               SVMEventClosure closure = new SVMEventClosure(pair.callback);
               closure.add(Value.createObject(e, "MouseEvent"));
               this.svm.postEvent(closure);
            }
         }
      }

   }

   public void mouseDragged(MouseEvent e) {
      if (this.jsListeners.isEmpty()) {
         this.fireMouseEvent(e);
      } else {
         Iterator var3 = this.jsListeners.iterator();

         while(var3.hasNext()) {
            SJSListenerPair pair = (SJSListenerPair)var3.next();
            if (pair.eventType.equals("drag")) {
               SVMEventClosure closure = new SVMEventClosure(pair.callback);
               closure.add(Value.createObject(e, "MouseEvent"));
               this.svm.postEvent(closure);
            }
         }
      }

   }

   private void fireMouseEvent(MouseEvent e) {
      if (this.state == 2) {
         this.state = 0;
         this.svm.push(Value.createObject(e, "MouseEvent"));
         this.svm.run();
         this.repaint();
      }

   }

   private void fireKeyEvent(KeyEvent e) {
      if (this.state == 2) {
         this.state = 0;
         this.svm.push(Value.createObject(e, "KeyEvent"));
         this.svm.run();
         this.repaint();
      }

   }
}
