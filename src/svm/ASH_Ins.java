package edu.stanford.cs.svm;

class ASH_Ins extends LogicalOp {
   public ASH_Ins() {
      super("ASH", 85);
   }

   public int applyInteger(int x, int y) {
      return y < 0 ? x >> -y : x << y;
   }
}
