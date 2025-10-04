package edu.stanford.cs.jsconsole;

import edu.stanford.cs.java2js.JSEvent;
import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JScrollPane;

public class JSConsole extends JScrollPane implements NBConsole {
   public static final Font DEFAULT_FONT = Font.decode("Courier New-Bold-18");
   public static final int DEFAULT_MARGIN = 2;
   private ArrayList<ActionListener> actionListeners = new ArrayList();
   private ConsoleTextPane textPane;
   private Font consoleFont;
   private StringBuffer buffer = null;

   public JSConsole() {
      super(20, 30);
      this.consoleFont = DEFAULT_FONT;
      this.textPane = new ConsoleTextPane(this);
      this.textPane.setFont(this.consoleFont);
      this.textPane.setBackground(Color.WHITE);
      this.textPane.setInputColor(Color.BLUE);
      this.textPane.setErrorColor(Color.RED);
      this.textPane.addFocusListener(new JSConsoleFocusListener(this));
      this.setViewportView(this.textPane);
      this.setMargin(2);
   }

   public void setMargin(int pixels) {
      this.setMargin(new Insets(pixels, pixels, pixels, pixels));
   }

   public void setMargin(Insets insets) {
      this.textPane.setMargin(insets);
   }

   public void clear() {
      this.textPane.clear();
   }

   public boolean isSwingComponent() {
      return true;
   }

   public void print(Object x) {
      if (this.buffer == null) {
         this.textPane.print(x.toString(), 0);
      } else {
         this.buffer.append(x);
      }

   }

   public void println() {
      if (this.buffer == null) {
         this.textPane.print("\n", 0);
      } else {
         this.buffer.append("\n");
      }

   }

   public void println(Object x) {
      this.print(x);
      this.println();
   }

   public void log(Object value) {
      this.println(value);
   }

   public void showErrorMessage(String msg) {
      this.textPane.print(msg + "\n", 2);
   }

   public void setFont(Font font) {
      super.setFont(font);
      this.consoleFont = font;
      if (this.textPane != null) {
         this.textPane.setFont(font);
      }

   }

   public Font getFont() {
      return this.consoleFont;
   }

   public void requestInput(String prompt) {
      this.requestFocus();
      this.print(prompt);
   }

   public void forceInput(String input) {
      this.textPane.print(input, 1);
      this.println();
      this.processLine(input);
   }

   public void requestFocus() {
      if (this.textPane != null) {
         this.textPane.requestFocus();
      }

   }

   public void addActionListener(ActionListener listener) {
      this.actionListeners.add(listener);
   }

   public void startConsoleLog() {
      this.buffer = new StringBuffer();
   }

   public String endConsoleLog() {
      String log = this.buffer.toString();
      this.buffer = null;
      return log;
   }

   public void processLine(String str) {
      ActionEvent e = new ActionEvent(this, 1001, str);
      JSEvent.dispatchList(this.actionListeners, e);
   }
}
