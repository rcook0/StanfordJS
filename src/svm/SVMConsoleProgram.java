package edu.stanford.cs.svm;

import edu.stanford.cs.jsconsole.JSConsole;
import java.awt.Dimension;
import java.awt.Font;

public class SVMConsoleProgram extends SVMProgram {
   private JSConsole console = new JSConsole();

   public SVMConsoleProgram() {
      this.console.setFont(Font.decode("Courier New-Bold-14"));
      this.add(this.console, "console");
      this.setPreferredSize(500, 300);
      this.console.addActionListener(new SVMConsoleListener(this.getSVM()));
      this.setConsole(this.console);
   }

   public void setPreferredSize(int width, int height) {
      this.console.setPreferredSize(new Dimension(width, height));
   }
}
