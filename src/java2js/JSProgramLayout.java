package edu.stanford.cs.java2js;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.util.HashMap;

class JSProgramLayout implements LayoutManager {
   private static final int MINIMUM = 0;
   private static final int PREFERRED = 1;
   private Component controls;
   private HashMap<Component, String> names = new HashMap();

   public JSProgramLayout() {
   }

   public void addLayoutComponent(String constraints, Component comp) {
      this.names.put(comp, constraints);
      if (constraints.equals("controls")) {
         this.controls = comp;
      }

   }

   public void removeLayoutComponent(Component comp) {
      this.names.remove(comp);
      if (comp == this.controls) {
         this.controls = null;
      }

   }

   public Dimension preferredLayoutSize(Container target) {
      return this.layoutSize(target, 1);
   }

   public Dimension minimumLayoutSize(Container target) {
      return this.layoutSize(target, 0);
   }

   private Dimension layoutSize(Container target, int mode) {
      synchronized(target.getTreeLock()) {
         Insets insets = target.getInsets();
         int width = 0;
         int height = 0;
         int extra = 0;
         int nComps = target.getComponentCount();

         for(int i = 0; i < nComps; ++i) {
            Component comp = target.getComponent(i);
            String name = (String)this.names.get(comp);
            if (name.equals("controls")) {
               extra = this.getModeSize(comp, mode).height;
               --nComps;
            } else {
               Dimension size = this.getModeSize(comp, mode);
               width += size.width;
               height = Math.max(height, size.height);
            }
         }

         width += insets.left + insets.right;
         height += insets.top + insets.bottom + extra;
         return new Dimension(width, height);
      }
   }

   public void layoutContainer(Container target) {
      synchronized(target.getTreeLock()) {
         Insets insets = target.getInsets();
         Rectangle frame = target.getBounds();
         frame.x += insets.left;
         frame.y += insets.top;
         frame.width -= insets.left + insets.right;
         frame.height -= insets.top + insets.bottom;
         Dimension size = new Dimension(0, 0);
         int nComps = target.getComponentCount();
         if (this.controls != null) {
            size = this.controls.getPreferredSize();
            --nComps;
         }

         if (nComps != 0) {
            frame.height -= size.height;
            int x = frame.x;
            int dx = frame.width / nComps;
            Component[] var12;
            int var11 = (var12 = target.getComponents()).length;

            for(int var10 = 0; var10 < var11; ++var10) {
               Component comp = var12[var10];
               String name = (String)this.names.get(comp);
               new Rectangle(frame);
               Rectangle r;
               if (name.equals("controls")) {
                  r = new Rectangle(frame.x, frame.y + frame.height, frame.width, size.height);
               } else {
                  r = new Rectangle(x, frame.y, dx, frame.height);
                  x += dx;
               }

               comp.setBounds(r.x, r.y, r.width, r.height);
            }

         }
      }
   }

   private Dimension getModeSize(Component comp, int mode) {
      if (mode == 0) {
         return comp.getMinimumSize();
      } else if (mode == 1) {
         return comp.getPreferredSize();
      } else {
         throw new RuntimeException("Illegal mode");
      }
   }
}
