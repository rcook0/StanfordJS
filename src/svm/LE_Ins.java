package edu.stanford.cs.svm;

class LE_Ins extends RelationalOp {
   public LE_Ins() {
      super("LE", 51);
   }

   public boolean applyInteger(int x, int y) {
      return x <= y;
   }

   public boolean applyDouble(double x, double y) {
      return x <= y;
   }
}
