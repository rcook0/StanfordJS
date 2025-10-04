package edu.stanford.cs.java2js;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import javax.swing.JScrollPane;
import javax.swing.JViewport;

public class JSScrollPane extends JScrollPane {
   public JSScrollPane() {
   }

   public JSScrollPane(Component view) {
      super(view);
   }

   public JSScrollPane(Component view, int vsbPolicy, int hsbPolicy) {
      super(view, vsbPolicy, hsbPolicy);
   }

   public JSScrollPane(int vsbPolicy, int hsbPolicy) {
      super(vsbPolicy, hsbPolicy);
   }

   public Point getViewPosition() {
      return this.getViewport().getViewPosition();
   }

   public void scrollRectToVisible(Rectangle r) {
      JViewport viewport = this.getViewport();
      Dimension size = viewport.getSize();
      Dimension vsize = viewport.getView().getSize();
      Point corner = viewport.getViewPosition();
      int x = r.x - corner.x;
      if (x < 0 || x >= size.width - r.width) {
         corner.x = Math.max(0, Math.min(r.x, vsize.width - size.width));
      }

      int y = r.y - corner.y;
      if (y < 0 || y >= size.height - r.height) {
         corner.y = Math.max(0, Math.min(r.y, vsize.height - size.height));
      }

      viewport.setViewPosition(corner);
   }
}
