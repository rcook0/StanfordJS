package edu.stanford.cs.console;

import java.awt.Font;
import javax.swing.JFrame;

public class ConsoleWindow extends JFrame implements Console {
   public static final Font DEFAULT_FONT = Font.decode("Monospaced-12");
   public static final int DEFAULT_WIDTH = 500;
   public static final int DEFAULT_HEIGHT = 300;
   private ConsolePane cpane;

   public ConsoleWindow() {
      this(500.0D, 300.0D);
   }

   public ConsoleWindow(double width, double height) {
      super("Console Window");
      this.cpane = new ConsolePane();
      this.setFont(DEFAULT_FONT);
      this.cpane.setSize((int)Math.round(width), (int)Math.round(height));
      this.cpane.setPreferredSize(this.cpane.getSize());
      this.add(this.cpane);
      this.pack();
      this.setDefaultCloseOperation(3);
      this.setVisible(true);
   }

   public void clear() {
      this.cpane.clear();
   }

   public void print(Object value) {
      this.cpane.print(value.toString());
   }

   public void println() {
      this.cpane.print("\n");
   }

   public void println(Object value) {
      this.print(value);
      this.println();
   }

   public void printf(String format, Object... args) {
      this.print(String.format(format, args));
   }

   public void format(String format, Object... args) {
      System.out.printf(format, args);
   }

   public String nextLine() {
      return this.cpane.nextLine();
   }

   public String nextLine(String prompt) {
      if (prompt != null) {
         this.print(prompt);
      }

      return this.nextLine();
   }

   public int nextInt() {
      return this.nextInt((String)null);
   }

   public int nextInt(String prompt) {
      while(true) {
         String line = this.nextLine(prompt);

         try {
            return Integer.parseInt(line);
         } catch (NumberFormatException var4) {
            this.println("Illegal integer format");
            if (prompt == null) {
               prompt = "Retry: ";
            }
         }
      }
   }

   public double nextDouble() {
      return this.nextDouble((String)null);
   }

   public double nextDouble(String prompt) {
      while(true) {
         String line = this.nextLine(prompt);

         try {
            return Double.parseDouble(line);
         } catch (NumberFormatException var4) {
            this.println("Illegal floating-point format");
            if (prompt == null) {
               prompt = "Retry: ";
            }
         }
      }
   }

   public void setFont(Font font) {
      super.setFont(font);
      if (this.cpane != null) {
         this.cpane.setFont(font);
      }

   }

   public void setFont(String str) {
      this.setFont(Font.decode(str));
   }
}
