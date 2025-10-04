package edu.stanford.cs.jseditor;

import java.awt.Color;
import java.awt.Point;
import java.util.HashMap;
import javax.swing.JTextPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.text.DefaultHighlighter.DefaultHighlightPainter;
import javax.swing.text.Highlighter.HighlightPainter;

class JSETextPane extends JTextPane implements DocumentListener {
   private HashMap<Color, HighlightPainter> highlighters;
   private JSEditor editor;
   private LineHighlighter highlighter;
   private Style style;
   private StyledDocument doc;
   private boolean lineWrap;

   public JSETextPane(JSEditor editor) {
      this.editor = editor;
      this.highlighters = new HashMap();
      this.doc = this.getStyledDocument();
      this.style = this.doc.getLogicalStyle(0);
      this.doc.addDocumentListener(this);
      this.highlighter = new LineHighlighter();
      this.setHighlighter(this.highlighter);
      this.setOpaque(true);
   }

   public boolean getScrollableTracksViewportWidth() {
      return this.lineWrap || this.getUI().getPreferredSize(this).width <= this.getParent().getSize().width;
   }

   public HighlighterKey addBackgroundHighlight(int p1, int p2, Color color) {
      Object id = null;

      try {
         id = this.highlighter.addHighlight(p1, p2, this.getPainterForColor(color));
      } catch (BadLocationException var6) {
      }

      this.repaint();
      return id == null ? null : new HighlighterKey(id);
   }

   public void removeBackgroundHighlight(HighlighterKey key) {
      this.highlighter.removeHighlight(key.getTag());
   }

   public Marker createMarker(int offset) {
      try {
         return new Marker(this.doc.createPosition(offset));
      } catch (BadLocationException var3) {
         throw new RuntimeException(var3.toString());
      }
   }

   public int getOffset(int x, int y) {
      return this.viewToModel(new Point(x, y));
   }

   public int getLineNumber(int offset) {
      String text = this.getText();
      int p = 0;
      int lineNumber = 0;

      while(p <= offset) {
         p = text.indexOf("\n", p) + 1;
         ++lineNumber;
         if (p == 0) {
            break;
         }
      }

      return lineNumber;
   }

   public int getLineStart(int line) {
      String text = this.getText();
      int p = 0;

      do {
         --line;
         if (line <= 0) {
            return p;
         }

         p = text.indexOf("\n", p) + 1;
      } while(p != 0);

      return text.length();
   }

   public void setLineWrap(boolean flag) {
      this.lineWrap = flag;
   }

   public boolean getLineWrap() {
      return this.lineWrap;
   }

   public void setTabs(int tabWidth) {
      FixedTabSet tabSet = new FixedTabSet(tabWidth);
      this.style.addAttribute(StyleConstants.TabSet, tabSet);
   }

   public void changedUpdate(DocumentEvent e) {
      this.editor.setSaveNeeded(true);
      this.editor.fireChangeListeners();
   }

   public void insertUpdate(DocumentEvent e) {
      this.editor.fireChangeListeners();
   }

   public void removeUpdate(DocumentEvent e) {
      this.editor.fireChangeListeners();
   }

   private HighlightPainter getPainterForColor(Color color) {
      HighlightPainter painter = (HighlightPainter)this.highlighters.get(color);
      if (painter == null) {
         painter = new DefaultHighlightPainter(color);
         this.highlighters.put(color, painter);
      }

      return (HighlightPainter)painter;
   }
}
