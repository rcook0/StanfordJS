package edu.stanford.cs.jsconsole;

import java.io.IOException;

class JavaConsoleMonitor implements Runnable {
   private JavaConsole console;

   public JavaConsoleMonitor(JavaConsole console) {
      this.console = console;
   }

   public void run() {
      try {
         String line = "";
         int lastChar = -1;

         while(true) {
            int ch = System.in.read();
            if (ch == -1) {
               line = null;
               break;
            }

            if (ch == 10) {
               if (lastChar != 13) {
                  break;
               }
            } else if (ch == 13) {
               if (lastChar != 10) {
                  break;
               }
            } else {
               line = line + (char)ch;
            }

            lastChar = ch;
         }

         this.console.processInput(line);
      } catch (IOException var4) {
         this.console.processInput((String)null);
      }

   }
}
