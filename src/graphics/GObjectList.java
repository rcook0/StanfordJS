package edu.stanford.cs.graphics;

import java.awt.Graphics;
import java.util.ArrayList;

class GObjectList {
   private transient GContainer parent;
   private ArrayList<GObject> contents;
   private ArrayList<GObject> enabledList;

   public GObjectList(GContainer container) {
      this.parent = container;
      this.contents = new ArrayList();
      if (this.parent instanceof GCanvas) {
         this.enabledList = new ArrayList();
      }

   }

   public GObjectList(GContainer container, GObjectList list) {
      this.parent = container;
      this.contents = new ArrayList();
      this.enabledList = new ArrayList();
      int nElements = list.contents.size();

      for(int i = 0; i < nElements; ++i) {
         this.contents.add((GObject)((GObject)list.contents.get(i)).clone());
      }

   }

   public synchronized void add(GObject gobj) {
      if (gobj.getParent() != null) {
         gobj.getParent().remove(gobj);
      }

      gobj.setParent(this.parent);
      this.contents.add(gobj);
      if (this.enabledList != null && gobj.areMouseListenersEnabled()) {
         this.enabledList.add(gobj);
      }

   }

   public synchronized void remove(GObject gobj) {
      this.contents.remove(gobj);
      gobj.setParent((GContainer)null);
      if (this.enabledList != null) {
         this.enabledList.remove(gobj);
      }

   }

   public synchronized void removeAll() {
      this.contents.clear();
      if (this.enabledList != null) {
         this.enabledList.clear();
      }

   }

   public int getElementCount() {
      return this.contents.size();
   }

   public GObject getElement(int index) {
      return (GObject)this.contents.get(index);
   }

   public synchronized GObject getElementAt(double x, double y, boolean requireEnabled) {
      ArrayList<GObject> list = requireEnabled ? this.enabledList : this.contents;

      for(int i = list.size() - 1; i >= 0; --i) {
         GObject gobj = (GObject)list.get(i);
         if (gobj.contains(x, y)) {
            return gobj;
         }
      }

      return null;
   }

   public synchronized void sendToFront(GObject gobj) {
      int index = this.contents.indexOf(gobj);
      if (index >= 0) {
         this.contents.remove(index);
         this.contents.add(gobj);
      }

   }

   public synchronized void sendToBack(GObject gobj) {
      int index = this.contents.indexOf(gobj);
      if (index >= 0) {
         this.contents.remove(index);
         this.contents.add(0, gobj);
      }

   }

   public synchronized void sendForward(GObject gobj) {
      int index = this.contents.indexOf(gobj);
      if (index >= 0) {
         this.contents.remove(index);
         this.contents.add(Math.min(this.contents.size(), index + 1), gobj);
      }

   }

   public synchronized void sendBackward(GObject gobj) {
      int index = this.contents.indexOf(gobj);
      if (index >= 0) {
         this.contents.remove(index);
         this.contents.add(Math.max(0, index - 1), gobj);
      }

   }

   public synchronized void mapPaint(Graphics g) {
      int nElements = this.contents.size();

      for(int i = 0; i < nElements; ++i) {
         ((GObject)this.contents.get(i)).paint(g);
      }

   }

   public synchronized boolean areMouseListenersEnabled() {
      int nElements = this.contents.size();

      for(int i = 0; i < nElements; ++i) {
         GObject gobj = (GObject)this.contents.get(i);
         if (gobj.areMouseListenersEnabled()) {
            return true;
         }
      }

      return false;
   }

   public synchronized void updateEnabledList() {
      this.enabledList.clear();
      int nElements = this.contents.size();

      for(int i = 0; i < nElements; ++i) {
         GObject gobj = (GObject)this.contents.get(i);
         if (gobj.areMouseListenersEnabled()) {
            this.enabledList.add(gobj);
         }
      }

   }
}
