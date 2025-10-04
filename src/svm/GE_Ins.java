package edu.stanford.cs.svm;

class GE_Ins extends RelationalOp {
   public GE_Ins() {
      super("GE", 53);
   }

   public boolean applyInteger(int x, int y) {
      return x >= y;
   }

   public boolean applyDouble(double x, double y) {
      return x >= y;
   }
}
