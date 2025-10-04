package edu.stanford.cs.jsconsole;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JTextPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

class ConsoleTextPane extends JTextPane implements KeyListener {
   public static final int OUTPUT_STYLE = 0;
   public static final int INPUT_STYLE = 1;
   public static final int ERROR_STYLE = 2;
   private Document document;
   private JSConsole console;
   private SimpleAttributeSet errorAttributes;
   private SimpleAttributeSet inputAttributes;
   private SimpleAttributeSet outputAttributes;
   private int base;
   private int lastChar;

   public ConsoleTextPane(JSConsole console) {
      this.console = console;
      this.addKeyListener(this);
      this.document = this.getDocument();
      this.outputAttributes = new SimpleAttributeSet();
      this.inputAttributes = new SimpleAttributeSet();
      this.errorAttributes = new SimpleAttributeSet();
      this.base = 0;
      this.lastChar = -1;
   }

   public void print(String str, int style) {
      this.insert(str, this.base, style);
      this.base += str.length();
      this.setCaretPosition(this.base);
   }

   public void clear() {
      this.setText("");
      this.base = 0;
   }

   public String getText() {
      return this.getText();
   }

   public String getText(int start, int end) {
      try {
         return this.document.getText(start, end - start);
      } catch (BadLocationException var4) {
         throw new RuntimeException(var4);
      }
   }

   public int getLength() {
      return this.document.getLength();
   }

   public void cut() {
      this.copy();
      this.deleteSelection();
   }

   public void paste() {
      if (this.getSelectionEnd() == this.document.getLength()) {
         int start = this.deleteSelection();
         this.setSelectionStart(start);
         super.paste();
         this.select(this.document.getLength(), this.document.getLength());
         if (this.document instanceof DefaultStyledDocument) {
            DefaultStyledDocument doc = (DefaultStyledDocument)this.document;
            doc.setCharacterAttributes(start, this.getSelectionEnd() - start, this.inputAttributes, true);
         }

      }
   }

   public boolean isPointSelection() {
      return this.getSelectionStart() == this.getSelectionEnd();
   }

   public void setInputStyle(int style) {
      if (this.getLength() != 0) {
         throw new RuntimeException("Console styles and colors cannot be changed after I/O has started.");
      } else {
         this.inputAttributes.addAttribute(StyleConstants.Bold, new Boolean((style & 1) != 0));
         this.inputAttributes.addAttribute(StyleConstants.Italic, new Boolean((style & 2) != 0));
      }
   }

   public void setInputColor(Color color) {
      if (this.getLength() != 0) {
         throw new RuntimeException("Console styles and colors cannot be changed after I/O has started.");
      } else {
         this.inputAttributes.addAttribute(StyleConstants.Foreground, color);
      }
   }

   public void setErrorStyle(int style) {
      if (this.getLength() != 0) {
         throw new RuntimeException("Console styles and colors cannot be changed after I/O has started.");
      } else {
         this.errorAttributes.addAttribute(StyleConstants.Bold, new Boolean((style & 1) != 0));
         this.errorAttributes.addAttribute(StyleConstants.Italic, new Boolean((style & 2) != 0));
      }
   }

   public void setErrorColor(Color color) {
      if (this.getLength() != 0) {
         throw new RuntimeException("Console styles and colors cannot be changed after I/O has started.");
      } else {
         this.errorAttributes.addAttribute(StyleConstants.Foreground, color);
      }
   }

   public void keyTyped(KeyEvent e) {
      if (!e.isMetaDown() && !e.isControlDown()) {
         this.processChar(e.getKeyChar());
         e.consume();
      }
   }

   public void keyPressed(KeyEvent e) {
      if (!e.isMetaDown() && !e.isControlDown()) {
         switch(e.getKeyCode()) {
         case 37:
            this.processChar(2);
         case 38:
         default:
            break;
         case 39:
            this.processChar(6);
         }

         e.consume();
      }
   }

   public void keyReleased(KeyEvent e) {
      if (!e.isMetaDown() && !e.isControlDown()) {
         e.consume();
      }
   }

   private void processChar(int ch) {
      if (ch == 10) {
         if (this.lastChar != 13) {
            this.signalEndOfLine();
         }
      } else if (ch == 13) {
         if (this.lastChar != 10) {
            this.signalEndOfLine();
         }
      } else {
         if (this.getCaretPosition() < this.base) {
            this.setCaretPosition(this.getLength());
         }

         int dot = this.getSelectionStart();
         switch(ch) {
         case 1:
            this.selectAll();
            dot = -1;
            break;
         case 2:
            dot = Math.max(this.getSelectionStart() - 1, this.base);
            break;
         case 3:
            this.copy();
            dot = -1;
            break;
         case 6:
            dot = Math.min(this.getSelectionEnd() + 1, this.getLength());
            break;
         case 8:
         case 127:
            if (dot == this.getSelectionEnd()) {
               if (dot > this.base) {
                  this.delete(dot - 1, dot);
                  --dot;
               }
            } else {
               dot = this.deleteSelection();
            }
            break;
         case 22:
            this.paste();
            dot = -1;
            break;
         case 24:
            this.cut();
            dot = -1;
            break;
         default:
            if (dot != this.getSelectionEnd()) {
               dot = this.deleteSelection();
            }

            this.insert("" + (char)ch, dot, 1);
            ++dot;
         }

         if (dot != -1) {
            this.select(dot, dot);
            this.setCaretPosition(dot);
         }
      }

      this.lastChar = ch;
   }

   private void signalEndOfLine() {
      int len = this.getLength() - this.base;
      String line = this.getText(this.base, this.base + len);
      this.insert("\n", this.base + len, 0);
      this.base += len + 1;
      this.console.processLine(line);
   }

   private void insert(String str, int dot, int style) {
      try {
         SimpleAttributeSet attributes = this.outputAttributes;
         switch(style) {
         case 1:
            attributes = this.inputAttributes;
            break;
         case 2:
            attributes = this.errorAttributes;
         }

         this.document.insertString(dot, str, attributes);
      } catch (BadLocationException var5) {
      }

   }

   private void delete(int p1, int p2) {
      try {
         this.document.remove(p1, p2 - p1);
      } catch (BadLocationException var4) {
         throw new RuntimeException(var4);
      }
   }

   private int deleteSelection() {
      int start = Math.max(this.base, this.getSelectionStart());
      int end = this.getSelectionEnd();
      if (end <= this.base) {
         return this.getLength();
      } else {
         this.delete(start, end);
         return start;
      }
   }

   protected void setStyleFromAttributes(Graphics g, AttributeSet attributes) {
      Font oldFont = this.getFont();
      int style = 0;
      if ((Boolean)attributes.getAttribute(StyleConstants.Bold)) {
         style |= 1;
      }

      if ((Boolean)attributes.getAttribute(StyleConstants.Italic)) {
         style |= 2;
      }

      g.setFont(new Font(oldFont.getName(), style, oldFont.getSize()));
      Color color = (Color)attributes.getAttribute(StyleConstants.Foreground);
      if (color == null) {
         color = this.getForeground();
      }

      g.setColor(color);
   }
}
