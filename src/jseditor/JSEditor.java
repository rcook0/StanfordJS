package edu.stanford.cs.jseditor;

import edu.stanford.cs.java2js.JSFrame;
import edu.stanford.cs.java2js.JSPanel;
import edu.stanford.cs.java2js.JSPlatform;
import edu.stanford.cs.java2js.JSScrollPane;
import edu.stanford.cs.java2js.JSTextSource;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JScrollBar;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class JSEditor extends JSPanel implements JSTextSource {
   public static final Font DEFAULT_FONT = Font.decode("Monospaced-Bold-12");
   public static final boolean DEFAULT_LINE_WRAP = true;
   public static final int DEFAULT_MARGIN = 2;
   public static final int DEFAULT_SCROLL_TARGET = 2;
   public static final int DEFAULT_TAB_SIZE = 3;
   private ArrayList<AdjustmentListener> adjustmentListeners = new ArrayList();
   private ArrayList<ChangeListener> changeListeners = new ArrayList();
   private EditorMode mode;
   private FindAndReplaceDialog findAndReplaceDialog;
   private HashMap<Color, SimpleAttributeSet> colorAttributes;
   private JSETextPane textPane = new JSETextPane(this);
   private JSFrame frame;
   private JSScrollPane scrollPane = new JSScrollPane();
   private JSEditorUndoHandler undoHandler;
   private String pathname;
   private boolean needsSaving;
   private boolean undoable;
   private int scrollTarget;
   private int tabSize;

   public JSEditor() {
      this.scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
      this.scrollPane.setViewportView(this.textPane);
      JScrollBar scrollBar = this.scrollPane.getVerticalScrollBar();
      JSScrollBarListener listener = new JSScrollBarListener(this, scrollBar);
      scrollBar.addAdjustmentListener(listener);
      this.setLayout(new BorderLayout());
      this.add(this.scrollPane, "Center");
      this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
      this.pathname = null;
      this.tabSize = 3;
      this.setFont(DEFAULT_FONT);
      this.setMargin(2);
      this.setLineWrap(true);
      this.setOpaque(false);
      this.colorAttributes = new HashMap();
      this.textPane.addFocusListener(new JSEditorFocusListener(this));
      this.findAndReplaceDialog = null;
      this.frame = null;
      this.scrollTarget = 2;
      this.needsSaving = false;
      this.undoable = true;
      this.undoHandler = new JSEditorUndoHandler(this);
      this.textPane.getDocument().addUndoableEditListener(this.undoHandler);
   }

   public void setTabSize(int columns) {
      this.tabSize = columns;
      this.updateTabStops();
   }

   public void setMargin(int pixels) {
      this.setMargin(new Insets(pixels, pixels, pixels, pixels));
   }

   public void setMargin(Insets insets) {
      this.textPane.setMargin(insets);
   }

   public Insets getMargin() {
      return this.textPane.getMargin();
   }

   public HighlighterKey addBackgroundHighlight(int line, Color color) {
      int p1 = this.getLineStart(line);
      int p2 = this.getLineStart(line + 1);
      return this.addBackgroundHighlight(p1, p2, color);
   }

   public HighlighterKey addBackgroundHighlight(int p1, int p2, Color color) {
      return this.textPane.addBackgroundHighlight(p1, p2, color);
   }

   public void removeBackgroundHighlight(HighlighterKey key) {
      this.textPane.removeBackgroundHighlight(key);
   }

   public void requestFocus() {
      if (this.textPane != null) {
         this.textPane.requestFocus();
      }

   }

   public void setFont(Font font) {
      super.setFont(font);
      if (this.textPane != null) {
         this.textPane.setFont(font);
      }

      this.updateTabStops();
   }

   public int getCursorPosition() {
      return this.textPane.getCaretPosition();
   }

   public void setCursorPosition(int offset) {
      this.textPane.setCaretPosition(offset);
   }

   public void select(int start, int end) {
      this.textPane.setCaretPosition(start);
      this.textPane.moveCaretPosition(end);
      this.textPane.repaint();
   }

   public void setText(String text) {
      this.textPane.setText(text);
   }

   public String getText() {
      return this.textPane.getText();
   }

   public void showFindAndReplaceDialog() {
      if (this.findAndReplaceDialog == null) {
         this.findAndReplaceDialog = this.createFindAndReplaceDialog();
         this.findAndReplaceDialog.centerOnParent();
      }

      this.findAndReplaceDialog.setVisible(true);
   }

   public boolean findNext(String str) {
      String text = this.textPane.getText();
      int pos = text.indexOf(str, this.textPane.getCaretPosition());
      if (pos == -1) {
         return false;
      } else {
         this.textPane.setCaretPosition(pos);
         this.textPane.moveCaretPosition(pos + str.length());
         this.textPane.repaint();
         return true;
      }
   }

   public boolean replace(String str, String replacement) {
      if (!str.equals(this.textPane.getSelectedText())) {
         return false;
      } else {
         this.textPane.replaceSelection(replacement);
         this.textPane.repaint();
         return true;
      }
   }

   public void replaceAll(String str, String replacement) {
      if (str.equals(this.textPane.getSelectedText())) {
         this.textPane.replaceSelection(replacement);
      }

      while(this.findNext(str)) {
         this.textPane.replaceSelection(replacement);
      }

      this.textPane.repaint();
   }

   public void setFrame(JSFrame frame) {
      this.frame = frame;
   }

   public JSFrame getFrame() {
      return this.frame;
   }

   public void setPathname(String pathname) {
      this.pathname = pathname;
   }

   public String getPathname() {
      return this.pathname;
   }

   public void setLineWrap(boolean flag) {
      this.textPane.setLineWrap(flag);
      this.repaint();
   }

   public boolean getLineWrap() {
      return this.textPane.getLineWrap();
   }

   public void setSaveNeeded(boolean flag) {
      this.needsSaving = flag;
   }

   public boolean isSaveNeeded() {
      return this.needsSaving;
   }

   public void setScrollTarget(int target) {
      this.scrollTarget = target;
   }

   public int getScrollTarget() {
      return this.scrollTarget;
   }

   public String getLine(int k) {
      String[] lines = JSPlatform.splitLines(this.textPane.getText());
      return k > 0 && k <= lines.length ? lines[k - 1] : null;
   }

   public int getLineNumber(int offset) {
      return this.textPane.getLineNumber(offset);
   }

   public void ensureLineVisible(int k) {
      try {
         int start = this.getLineStart(Math.max(1, k - this.scrollTarget));
         int current = this.getLineStart(k);
         Rectangle r = this.textPane.modelToView(start);
         r.add(this.textPane.modelToView(current));
         if (r != null) {
            this.scrollPane.scrollRectToVisible(r);
         }
      } catch (BadLocationException var5) {
      }

   }

   public int getLineHeight() {
      return this.textPane.getFontMetrics(this.textPane.getFont()).getHeight();
   }

   public FontMetrics getTextPaneMetrics() {
      return this.textPane.getFontMetrics(this.textPane.getFont());
   }

   public int pointToOffset(Point pt) {
      Point corner = this.scrollPane.getViewPosition();
      return this.textPane.viewToModel(new Point(pt.x + corner.x, pt.y + corner.y));
   }

   public Point offsetToPoint(int index) {
      try {
         Point corner = this.scrollPane.getViewPosition();
         Rectangle r = this.textPane.modelToView(index);
         FontMetrics fm = this.textPane.getFontMetrics(this.textPane.getFont());
         return new Point(r.x - corner.x, r.y - corner.y + fm.getAscent());
      } catch (BadLocationException var5) {
         throw new RuntimeException(var5);
      }
   }

   public int getTopLine() {
      Point corner = this.scrollPane.getViewPosition();
      return this.getLineNumber(this.textPane.viewToModel(corner));
   }

   public void setUndoableFlag(boolean flag) {
      this.undoable = flag;
   }

   public boolean getUndoableFlag() {
      return this.undoable;
   }

   public void setEditorMode(EditorMode mode) {
      this.mode = mode;
   }

   public EditorMode getEditorMode() {
      return this.mode;
   }

   public void setTextColor(int p1, int p2, Color color) {
      SimpleAttributeSet aset = (SimpleAttributeSet)this.colorAttributes.get(color);
      if (aset == null) {
         aset = new SimpleAttributeSet();
         aset.addAttribute(StyleConstants.Foreground, color);
         this.colorAttributes.put(color, aset);
      }

      StyledDocument doc = this.textPane.getStyledDocument();
      doc.setCharacterAttributes(p1, p2 - p1, aset, true);
   }

   public int getLineStart(int k) {
      return this.getLineRange(k).getStart();
   }

   public OffsetRange getLineRange(int k) {
      String text = this.textPane.getText();
      int n = text.length();
      int start = 0;
      int lineNumber = 1;
      boolean started = k == 1;

      for(int i = 0; i < n; ++i) {
         char ch = text.charAt(i);
         boolean eol = ch == '\n' || ch == '\r';
         if (eol) {
            if (started) {
               return new OffsetRange(start, i);
            }

            int skipChar = 23 - ch;
            if (i < n - 1 && text.charAt(i + 1) == skipChar) {
               ++i;
            }

            ++lineNumber;
            if (lineNumber == k) {
               start = i + 1;
               started = true;
            }
         }
      }

      return new OffsetRange(start, n);
   }

   public Marker createMarker(int offset) {
      return this.textPane.createMarker(offset);
   }

   public void addAdjustmentListener(AdjustmentListener listener) {
      this.adjustmentListeners.add(listener);
   }

   public void removeAdjustmentListener(AdjustmentListener listener) {
      this.adjustmentListeners.remove(listener);
   }

   public void fireAdjustmentListeners(AdjustmentEvent e) {
      Iterator var3 = this.adjustmentListeners.iterator();

      while(var3.hasNext()) {
         AdjustmentListener listener = (AdjustmentListener)var3.next();
         listener.adjustmentValueChanged(e);
      }

   }

   public void addChangeListener(ChangeListener listener) {
      this.changeListeners.add(listener);
   }

   public void removeChangeListener(ChangeListener listener) {
      this.changeListeners.remove(listener);
   }

   public void fireChangeListeners() {
      Iterator var2 = this.changeListeners.iterator();

      while(var2.hasNext()) {
         ChangeListener listener = (ChangeListener)var2.next();
         listener.stateChanged(new ChangeEvent(this));
      }

   }

   public AbstractAction getUndoAction() {
      return this.undoHandler.getUndoAction();
   }

   public AbstractAction getRedoAction() {
      return this.undoHandler.getRedoAction();
   }

   public void printDebugData() {
      System.out.println("caret = " + this.textPane.getCaretPosition());
      System.out.println("selection = " + this.textPane.getSelectedText());
   }

   protected FindAndReplaceDialog createFindAndReplaceDialog() {
      return new FindAndReplaceDialog(this);
   }

   private void updateTabStops() {
      if (this.textPane != null) {
         Font font = this.textPane.getFont();
         int tabWidth = this.tabSize * this.textPane.getFontMetrics(font).stringWidth(" ");
         this.textPane.setTabs(tabWidth);
      }
   }
}
