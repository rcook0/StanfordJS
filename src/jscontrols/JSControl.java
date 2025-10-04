package edu.stanford.cs.jscontrols;

import edu.stanford.cs.controller.Control;
import edu.stanford.cs.controller.Controller;
import edu.stanford.cs.controller.Updatable;
import edu.stanford.cs.controller.Updater;
import edu.stanford.cs.java2js.JSEvent;
import edu.stanford.cs.java2js.JSImage;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.event.ChangeEvent;

public abstract class JSControl extends JSControlComponent implements Control, Updatable {
   public static final int CONTROL_SIZE = 48;
   private ArrayList<ActionListener> listeners = new ArrayList();
   private Controller controller;
   private JSImage controlIcon;
   private JSImage disabledIcon;
   private JSImage pressedIcon;
   private JSImage rolloverIcon;
   private MouseEvent triggerEvent;
   private String actionCommand;
   private String name;
   private Updater updater;
   private boolean entered;
   private boolean pressed;

   protected JSControl() {
      this.setPreferredSize(new Dimension(48, 48));
      this.setMinimumSize(new Dimension(48, 48));
      this.addMouseListener(this.createMouseListener());
      this.actionCommand = null;
      this.updater = null;
   }

   public void setName(String name) {
      this.name = name;
      this.setToolTipText(name);
   }

   public String getName() {
      return this.name;
   }

   public void setActionCommand(String cmd) {
      this.actionCommand = cmd;
   }

   public String getActionCommand() {
      return this.actionCommand == null ? this.getName() : this.actionCommand;
   }

   public void setController(Controller controller) {
      this.controller = controller;
   }

   public Controller getController() {
      return this.controller;
   }

   public void addActionListener(ActionListener listener) {
      this.listeners.add(listener);
   }

   public void removeActionListener(ActionListener listener) {
      this.listeners.remove(listener);
   }

   public void fireActionListeners() {
      String cmd = this.getActionCommand();
      int bits = this.triggerEvent == null ? 0 : this.triggerEvent.getModifiers();
      ActionEvent e = JSEvent.createActionEvent(this, cmd, bits);
      JSEvent.dispatchList(this.listeners, e);
   }

   public void stateChanged(ChangeEvent e) {
   }

   public void execute() {
      this.fireActionListeners();
   }

   public void update() {
      if (this.updater != null) {
         this.updater.update(this);
      }

   }

   public void setUpdater(Updater updater) {
      this.updater = updater;
   }

   protected void setIcon(JSImage icon) {
      this.controlIcon = icon;
   }

   protected void setDisabledIcon(JSImage icon) {
      this.disabledIcon = icon;
   }

   protected void setPressedIcon(JSImage icon) {
      this.pressedIcon = icon;
   }

   protected void setRolloverIcon(JSImage icon) {
      this.rolloverIcon = icon;
   }

   protected JSImage createImageIcon(String url) {
      return new JSImage(url);
   }

   protected MouseListener createMouseListener() {
      return new JSControl.JSControlListener();
   }

   protected void paintControl(Graphics g) {
      JSImage icon = this.controlIcon;
      if (this.isEnabled()) {
         if (this.pressed) {
            icon = this.pressedIcon;
         } else if (this.entered) {
            icon = this.rolloverIcon;
         }
      } else {
         icon = this.disabledIcon;
      }

      g.drawImage(icon, 0, 0, 48, 48, this);
   }

   class JSControlListener implements MouseListener {
      public void mouseClicked(MouseEvent e) {
      }

      public void mouseEntered(MouseEvent e) {
         if (JSControl.this.isEnabled()) {
            JSControl.this.entered = true;
            JSControl.this.repaint();
         }

      }

      public void mouseExited(MouseEvent e) {
         JSControl.this.entered = false;
         JSControl.this.repaint();
      }

      public void mousePressed(MouseEvent e) {
         if (JSControl.this.isEnabled()) {
            JSControl.this.pressed = true;
            JSControl.this.repaint();
         }

      }

      public void mouseReleased(MouseEvent e) {
         if (JSControl.this.isEnabled()) {
            if (JSControl.this.pressed && JSControl.this.entered) {
               JSControl.this.triggerEvent = e;
               JSControl.this.execute();
            }

            JSControl.this.pressed = false;
            JSControl.this.repaint();
         }

      }
   }
}
