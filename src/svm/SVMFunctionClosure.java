package edu.stanford.cs.svm;

public class SVMFunctionClosure {
   private int[] code;
   private int addr;
   private SVMStackFrame frame;

   public SVMFunctionClosure(int[] code, int addr, SVMStackFrame frame) {
      this.code = code;
      this.addr = addr;
      this.frame = frame;
   }

   public int getAddress() {
      return this.addr;
   }

   public int[] getCode() {
      return this.code;
   }

   public SVMStackFrame getFrame() {
      return this.frame;
   }

   public String toString() {
      return "FunctionClosure@" + this.addr;
   }
}
