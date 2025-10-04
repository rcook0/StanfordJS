package edu.stanford.cs.console;

import java.io.IOException;

public class SystemConsole implements Console {
   private int prevChar;

   public void print(Object value) {
      System.out.print(value);
   }

   public void println() {
      System.out.println();
   }

   public void println(Object value) {
      System.out.println(value);
   }

   public void printf(String format, Object... args) {
      System.out.printf(format, args);
   }

   public void format(String format, Object... args) {
      System.out.printf(format, args);
   }

   public String nextLine() {
      try {
         String line = "";

         while(true) {
            int ch = System.in.read();
            if (ch == -1) {
               return null;
            }

            if (ch == 13) {
               if (this.prevChar != 10) {
                  this.prevChar = ch;
                  break;
               }
            } else if (ch == 10) {
               if (this.prevChar != 13) {
                  this.prevChar = ch;
                  break;
               }
            } else {
               line = line + (char)ch;
            }

            this.prevChar = ch;
         }

         return line;
      } catch (IOException var3) {
         throw new RuntimeException(var3.toString());
      }
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
}
