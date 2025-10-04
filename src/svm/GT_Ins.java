package edu.stanford.cs.svm;

class GT_Ins extends RelationalOp {
   public GT_Ins() {
      super("GT", 52);
   }

   public boolean applyInteger(int x, int y) {
      return x > y;
   }

   public boolean applyDouble(double x, double y) {
      return x > y;
   }
}
