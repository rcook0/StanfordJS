package edu.stanford.cs.java2js;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.lang.reflect.Constructor;

class PSGraphicsWriter implements MouseListener {
   private Component comp;
   private Constructor<?> createPSGraphics;

   public PSGraphicsWriter(Component comp) {
      this.comp = comp;

      try {
         Class[] types = new Class[]{String.class, Component.class};
         Class<?> psGraphics = Class.forName("edu.stanford.cs.psgraphics.PSGraphics");
         this.createPSGraphics = psGraphics.getConstructor(types);
         comp.addMouseListener(this);
      } catch (Exception var4) {
      }

   }

   public void mouseClicked(MouseEvent e) {
      if (e.isShiftDown() && e.isMetaDown()) {
         try {
            Object[] args = new Object[]{"psgraphics.ps", this.comp};
            Graphics psg = (Graphics)this.createPSGraphics.newInstance(args);
            this.comp.paint(psg);
            psg.dispose();
         } catch (Exception var4) {
            var4.printStackTrace();
            System.out.println(var4);
         }
      }

   }

   public void mouseEntered(MouseEvent e) {
   }

   public void mouseExited(MouseEvent e) {
   }

   public void mousePressed(MouseEvent e) {
   }

   public void mouseReleased(MouseEvent e) {
   }
}
