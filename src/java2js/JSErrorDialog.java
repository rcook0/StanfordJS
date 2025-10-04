package edu.stanford.cs.java2js;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import javax.swing.JComponent;

public class JSErrorDialog extends JSDialog {
   public static final Color ERROR_BACKGROUND = new Color(16763955);
   public static final int DEFAULT_WIDTH = 350;
   public static final int DEFAULT_HEIGHT = 55;
   public static final int TOP_MARGIN = 15;
   public static final int LEFT_MARGIN = 1;
   private JSBugCanvas bugCanvas;
   private JSMessageArea messageArea;

   public JSErrorDialog(JComponent target) {
      this(target, true);
   }

   public JSErrorDialog(JComponent target, boolean isModal) {
      super(target, isModal);
      this.setLayout(new BorderLayout());
      this.setBackground(ERROR_BACKGROUND);
      this.bugCanvas = new JSBugCanvas();
      this.messageArea = new JSMessageArea();
      this.messageArea.setBackground(ERROR_BACKGROUND);
      this.add(this.bugCanvas, "West");
      this.add(this.messageArea, "Center");
      JSErrorDialogListener listener = new JSErrorDialogListener(this);
      this.bugCanvas.addMouseListener(listener);
      this.bugCanvas.addMouseMotionListener(listener);
      this.messageArea.addMouseListener(listener);
      this.messageArea.addMouseMotionListener(listener);
      this.setPreferredSize(new Dimension(350, 55));
   }

   public void setErrorMessage(String msg) {
      String style = msg.startsWith("<html>") ? "text/html" : "text/plain";
      Insets insets = new Insets(15, 1, 0, 0);
      this.messageArea.setMargin(insets);
      this.messageArea.setContentType(style);
      this.messageArea.setText(msg);
      this.messageArea.repaint();
   }
}
