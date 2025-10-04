package edu.stanford.cs.java2js;

import java.awt.Component;
import java.awt.Frame;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JDialog;
import javax.swing.SwingUtilities;

public class JSDialog extends JDialog {
   public ArrayList<ActionListener> listeners;
   public Component target;
   public Frame frame;

   public JSDialog(Component target) {
      this(target, true, (Frame)SwingUtilities.getWindowAncestor(target));
   }

   public JSDialog(Component target, boolean isModal) {
      this(target, isModal, (Frame)SwingUtilities.getWindowAncestor(target));
   }

   public void execute(String cmd) {
      this.setVisible(false);
      ActionEvent e = new ActionEvent(this, 1001, cmd);
      this.fireActionListeners(e);
   }

   public void addActionListener(ActionListener listener) {
      this.listeners.add(listener);
   }

   public void removeActionListener(ActionListener listener) {
      this.listeners.remove(listener);
   }

   public void fireActionListeners(ActionEvent e) {
      JSEvent.dispatchList(this.listeners, e);
   }

   public Component getTarget() {
      return this.target;
   }

   public Point getTargetCoordinates(Point pt) {
      int x = pt.x;
      int y = pt.y;

      for(Object c = this.target; c != null && c != this.frame; c = ((Component)c).getParent()) {
         x -= ((Component)c).getX();
         y -= ((Component)c).getY();
      }

      return new Point(x, y);
   }

   public Point getWindowCoordinates(Point pt) {
      int x = pt.x;
      int y = pt.y;

      for(Object c = this.target; c != null && c != this.frame; c = ((Component)c).getParent()) {
         x += ((Component)c).getX();
         y += ((Component)c).getY();
      }

      return new Point(x, y);
   }

   public void centerOnParent() {
      this.setLocationRelativeTo(this.target);
   }

   public void setTitle(String title) {
      this.setUndecorated(title == null);
      super.setTitle(title);
   }

   public void signalError(String msg) {
      throw new RuntimeException(msg);
   }

   private JSDialog(Component target, boolean isModal, Frame frame) {
      super(frame, isModal);
      this.target = target;
      this.frame = frame;
      this.listeners = new ArrayList();
      this.setUndecorated(true);
   }
}
