package edu.stanford.cs.jseditor;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

class FindAndReplaceListener implements ActionListener, MouseListener, MouseMotionListener {
   private FindAndReplaceDialog dialog;
   private JSEditor editor;
   private int deltaX;
   private int deltaY;

   public FindAndReplaceListener(JSEditor editor, FindAndReplaceDialog dialog) {
      this.editor = editor;
      this.dialog = dialog;
   }

   public void actionPerformed(ActionEvent e) {
      String cmd = e.getActionCommand();
      String find = this.dialog.getFindField();
      String replace = this.dialog.getReplaceField();
      if (cmd.equals("Find Next")) {
         if (this.editor.findNext(find)) {
            this.editor.requestFocus();
         } else {
            Toolkit.getDefaultToolkit().beep();
         }
      } else if (cmd.equals("Replace")) {
         if (this.editor.replace(find, replace)) {
            this.editor.requestFocus();
         } else {
            Toolkit.getDefaultToolkit().beep();
         }
      } else if (cmd.equals("Replace All")) {
         this.editor.replaceAll(find, replace);
         this.editor.requestFocus();
      } else if (cmd.equals("Cancel")) {
         this.dialog.setVisible(false);
      }

   }

   public void mouseClicked(MouseEvent e) {
      this.dialog.execute("Cancel");
      this.dialog.getTarget().requestFocus();
      e.consume();
   }

   public void mouseEntered(MouseEvent e) {
   }

   public void mouseExited(MouseEvent e) {
   }

   public void mousePressed(MouseEvent e) {
      this.deltaX = this.dialog.getX() - e.getX();
      this.deltaY = this.dialog.getY() - e.getY();
      e.consume();
   }

   public void mouseReleased(MouseEvent e) {
      e.consume();
   }

   public void mouseMoved(MouseEvent e) {
      e.consume();
   }

   public void mouseDragged(MouseEvent e) {
      this.dialog.setLocation(e.getX() + this.deltaX, e.getY() + this.deltaY);
      e.consume();
   }
}
