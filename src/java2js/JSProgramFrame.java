package edu.stanford.cs.java2js;

import javax.swing.JFrame;

class JSProgramFrame extends JFrame {
   public JSProgramFrame() {
      this.setDefaultCloseOperation(3);
      new PSGraphicsWriter(this);
   }
}
