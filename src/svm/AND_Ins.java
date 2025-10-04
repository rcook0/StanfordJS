package edu.stanford.cs.svm;

class AND_Ins extends LogicalOp {
   public AND_Ins() {
      super("AND", 81);
   }

   public int applyInteger(int x, int y) {
      return x & y;
   }
}
