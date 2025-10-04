package edu.stanford.cs.svm;

class ExceptionFrame {
   private int addr;
   private int depth;

   public ExceptionFrame(int addr, int depth) {
      this.addr = addr;
      this.depth = depth;
   }

   public int getDispatchAddress() {
      return this.addr;
   }

   public int getStackDepth() {
      return this.depth;
   }
}
