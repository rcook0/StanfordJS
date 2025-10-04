package edu.stanford.cs.java2js;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class JSStartupListener implements ActionListener {
   private JSProgram pgm;

   public JSStartupListener(JSProgram pgm) {
      this.pgm = pgm;
   }

   public void actionPerformed(ActionEvent e) {
      this.pgm.run();
   }
}
