package edu.stanford.cs.console;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

class ConsolePane extends JScrollPane implements KeyListener {
   private SimpleAttributeSet textAttributes;
   private SimpleAttributeSet inputAttributes;
   private JTextPane textPane = new JTextPane();
   private Document document;
   private int base;
   private CharacterQueue inputQueue;

   public ConsolePane() {
      super(22, 32);
      this.textPane.addKeyListener(this);
      this.setViewportView(this.textPane);
      this.document = this.textPane.getDocument();
      this.textAttributes = new SimpleAttributeSet();
      this.inputAttributes = new SimpleAttributeSet();
      this.inputAttributes.addAttribute(StyleConstants.Bold, true);
      this.inputAttributes.addAttribute(StyleConstants.Foreground, Color.BLUE);
      this.inputQueue = new CharacterQueue();
      this.base = 0;
   }

   public String nextLine() {
      this.base = this.document.getLength();
      this.textPane.setCaretPosition(this.base);

      while(true) {
         char ch = this.inputQueue.dequeue();
         if (ch == '\n' || ch == '\r') {
            int len = this.document.getLength() - this.base;

            try {
               String line = this.textPane.getText(this.base, len);
               this.insert(this.base + len, "\n", this.textAttributes);
               this.base += len + 1;
               return line;
            } catch (BadLocationException var3) {
               throw new RuntimeException(var3.toString());
            }
         }

         if (this.textPane.getCaretPosition() < this.base) {
            this.textPane.setCaretPosition(this.document.getLength());
         }

         int dot = this.textPane.getSelectionStart();
         switch(ch) {
         case '\u0002':
            dot = Math.max(this.textPane.getSelectionStart() - 1, this.base);
            break;
         case '\u0003':
            dot = -1;
            break;
         case '\u0006':
            dot = Math.min(this.textPane.getSelectionEnd() + 1, this.document.getLength());
            break;
         case '\b':
         case '\u007f':
            if (dot == this.textPane.getSelectionEnd()) {
               if (dot > this.base) {
                  this.delete(dot - 1, dot);
                  --dot;
               }
            } else {
               dot = this.deleteSelection();
            }
            break;
         case '\u0016':
            dot = -1;
            break;
         case '\u0018':
            dot = -1;
            break;
         default:
            if (dot != this.textPane.getSelectionEnd()) {
               dot = this.deleteSelection();
            }

            this.insert(dot, "" + ch, this.inputAttributes);
            ++dot;
         }

         if (dot != -1) {
            this.textPane.select(dot, dot);
            this.textPane.setCaretPosition(dot);
         }
      }
   }

   public void clear() {
      this.textPane.setText("");
      this.base = 0;
   }

   public void print(String str) {
      this.insert(this.document.getLength(), str, this.textAttributes);
      this.base = this.document.getLength();
      this.textPane.setCaretPosition(this.base);
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

   }

   public void keyTyped(KeyEvent e) {
      if (!e.isMetaDown() && !e.isControlDown()) {
         this.inputQueue.enqueue(e.getKeyChar());
         e.consume();
      }

   }

   public void keyPressed(KeyEvent e) {
      switch(e.getKeyCode()) {
      case 37:
         this.inputQueue.enqueue('\u0002');
      case 38:
      default:
         break;
      case 39:
         this.inputQueue.enqueue('\u0006');
      }

      e.consume();
   }

   public void keyReleased(KeyEvent e) {
      e.consume();
   }

   private void insert(int dot, String str, SimpleAttributeSet attributes) {
      try {
         this.document.insertString(dot, str, attributes);
      } catch (BadLocationException var5) {
         throw new RuntimeException(var5.toString());
      }
   }

   private void delete(int p1, int p2) {
      try {
         this.document.remove(p1, p2 - p1);
      } catch (BadLocationException var4) {
         throw new RuntimeException(var4.toString());
      }
   }

   private int deleteSelection() {
      int start = Math.max(this.base, this.textPane.getSelectionStart());
      int end = this.textPane.getSelectionEnd();
      if (end <= this.base) {
         return this.document.getLength();
      } else {
         this.delete(start, end);
         return start;
      }
   }
}
