package edu.stanford.cs.jseditor;

import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Rectangle;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.JTextComponent;
import javax.swing.text.Highlighter.Highlight;
import javax.swing.text.Highlighter.HighlightPainter;

public class LineHighlighter extends DefaultHighlighter {
   private JTextComponent comp;

   public void install(JTextComponent comp) {
      super.install(comp);
      this.comp = comp;
   }

   public void deinstall(JTextComponent comp) {
      super.deinstall(comp);
      comp = null;
   }

   public void paint(Graphics g) {
      Highlight[] highlights = this.getHighlights();
      int n = highlights.length;
      Rectangle r = this.comp.getBounds();
      Insets insets = this.comp.getInsets();
      r.x = insets.left;
      r.y = insets.top;
      r.height -= insets.top + insets.bottom;

      for(int i = 0; i < n; ++i) {
         Highlight h = highlights[i];
         String className = h.getClass().getName();
         if (className.indexOf("LayeredHighlightInfo") != -1) {
            HighlightPainter painter = h.getPainter();
            painter.paint(g, h.getStartOffset(), h.getEndOffset(), r, this.comp);
         }
      }

   }
}
