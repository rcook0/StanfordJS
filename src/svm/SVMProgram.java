package edu.stanford.cs.svm;

import edu.stanford.cs.java2js.JSProgram;
import edu.stanford.cs.jsconsole.NBConsole;

public class SVMProgram extends JSProgram {
   private static final int[] EMPTY_PROGRAM = new int[1];
   private SVM svm = this.createSVM();

   public SVMProgram() {
      this.svm.setProgram(this);
      String className = this.getClass().getName();
      this.setTitle(className.substring(className.lastIndexOf(".") + 1));
      this.setCode(EMPTY_PROGRAM);
   }

   public void init() {
   }

   public SVM getSVM() {
      return this.svm;
   }

   public boolean isCompiler() {
      return false;
   }

   public void setCode(int[] code) {
      this.svm.setCode(code);
   }

   public int[] getCode() {
      return this.svm.getCode();
   }

   public void setConsole(NBConsole console) {
      this.svm.setConsole(console);
   }

   public NBConsole getConsole() {
      return this.svm.getConsole();
   }

   public void run() {
      this.init();
      this.pack();
      this.setVisible(true);
      this.svm.run();
   }

   protected SVM createSVM() {
      return new SVM();
   }
}
