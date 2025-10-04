package edu.stanford.cs.sjs;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.Rectangle;

public class SJSLayout implements LayoutManager {
   private Component console = null;
   private Component controls = null;
   private Component editor = null;
   private Component gwindow = null;

   public void addLayoutComponent(String name, Component comp) {
      if (name.equals("controls")) {
         this.controls = comp;
      } else if (name.equals("console")) {
         this.console = comp;
      } else if (name.equals("editor")) {
         this.editor = comp;
      } else {
         if (!name.equals("gwindow")) {
            throw new RuntimeException("Illegal component name: " + name);
         }

         this.gwindow = comp;
      }

   }

   public void removeLayoutComponent(Component comp) {
      if (comp == this.gwindow) {
         this.gwindow = null;
      }

   }

   public Dimension preferredLayoutSize(Container target) {
      synchronized(target.getTreeLock()) {
         Dimension eSize = this.editor.getPreferredSize();
         Dimension cSize = this.console.getPreferredSize();
         Dimension ctlSize = this.controls.getPreferredSize();
         int width = 20 + eSize.width + cSize.width + 10;
         int height = 10 + cSize.height + 10 + ctlSize.height;
         Insets insets = target.getInsets();
         width += insets.left + insets.right;
         height += insets.top + insets.bottom;
         return new Dimension(width, height);
      }
   }

   public Dimension minimumLayoutSize(Container target) {
      return this.preferredLayoutSize(target);
   }

   public void layoutContainer(Container target) {
      synchronized(target.getTreeLock()) {
         Insets insets = target.getInsets();
         Dimension ctlSize = this.controls.getPreferredSize();
         Rectangle bounds = target.getBounds();
         int x = bounds.x + insets.left;
         int y = bounds.y + insets.top;
         int width = bounds.width - insets.left - insets.right;
         int height = bounds.height - insets.top - insets.bottom;
         int cx = x + 10;
         int cy = y + 10;
         int cw = 500;
         int ch = y + height - cy - 10 - ctlSize.height;
         int ex = cx + cw + 10;
         int ey = cy;
         int ew = x + width - ex - 10;
         int eh = ch;
         if (this.gwindow != null) {
            Dimension gSize = this.gwindow.getPreferredSize();
            this.gwindow.setBounds(cx, cy, gSize.width, gSize.height);
            cy += gSize.height + 10;
            ch -= gSize.height + 10;
         }

         this.console.setBounds(cx, cy, cw, ch);
         this.editor.setBounds(ex, ey, ew, eh);
         this.controls.setBounds(0, height - ctlSize.height, width, ctlSize.height);
      }
   }
}
