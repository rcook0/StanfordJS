package edu.stanford.cs.svm;

class LSH_Ins extends LogicalOp {
   public LSH_Ins() {
      super("LSH", 84);
   }

   public int applyInteger(int x, int y) {
      return y < 0 ? x >>> -y : x << y;
   }
}
