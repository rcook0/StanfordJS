package edu.stanford.cs.svm;

class LT_Ins extends RelationalOp {
   public LT_Ins() {
      super("LT", 50);
   }

   public boolean applyInteger(int x, int y) {
      return x < y;
   }

   public boolean applyDouble(double x, double y) {
      return x < y;
   }
}
