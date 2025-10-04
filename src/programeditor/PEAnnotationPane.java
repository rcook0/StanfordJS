package edu.stanford.cs.programeditor;

import edu.stanford.cs.java2js.JSCanvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BorderFactory;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

class PEAnnotationPane extends JSCanvas implements AdjustmentListener, ChangeListener, MouseListener {
   public static final Color ANNOTATION_BGCOLOR = new Color(15658734);
   public static final int ANNOTATION_WIDTH = 26;
   private ProgramEditor editor;
   private boolean needsUpdate;

   public PEAnnotationPane(ProgramEditor editor) {
      this.editor = editor;
      this.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.GRAY));
      this.addMouseListener(this);
   }

   public void paintComponent(Graphics g) {
      if (this.needsUpdate) {
         if (this.editor != null) {
            this.editor.updateHighlights();
         }

         this.needsUpdate = false;
      }

      Dimension size = this.getSize();
      g.setColor(ANNOTATION_BGCOLOR);
      g.fillRect(0, 0, size.width, size.height);
      FontMetrics fm = this.editor.getTextPaneMetrics();
      int h = this.editor.getLineHeight();
      int k = this.editor.getTopLine();
      int y0 = this.editor.offsetToPoint(this.editor.getLineStart(k)).y - fm.getAscent();

      for(Rectangle r = new Rectangle(0, y0, size.width, h); r.y < size.height && this.editor.getLine(k) != null; ++k) {
         this.editor.drawAnnotations(g, k, r);
         r.y += h;
      }

   }

   public Dimension getPreferredSize() {
      return new Dimension(26, 1);
   }

   public void mouseClicked(MouseEvent e) {
      int offset = this.editor.pointToOffset(new Point(0, e.getY()));
      if (offset >= 0) {
         int k = this.editor.getLineNumber(offset);
         if (this.editor.isBreakpoint(k)) {
            this.editor.removeBreakpoint(k);
         } else if (this.editor.isBreakpointLegal(k)) {
            this.editor.setBreakpoint(k);
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

   public void stateChanged(ChangeEvent e) {
      if (this.editor.getUndoableFlag()) {
         this.needsUpdate = true;
         this.editor.setParseNeeded(true);
         this.editor.clearErrorHighlight();
         this.repaint();
      }

   }

   public void adjustmentValueChanged(AdjustmentEvent e) {
      this.repaint();
   }
}
