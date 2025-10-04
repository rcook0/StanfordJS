package edu.stanford.cs.svm;

class XOR_Ins extends LogicalOp {
   public XOR_Ins() {
      super("XOR", 83);
   }

   public int applyInteger(int x, int y) {
      return x ^ y;
   }
}
