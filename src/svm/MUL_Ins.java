package edu.stanford.cs.svm;

class MUL_Ins extends ArithmeticOp {
   public MUL_Ins() {
      super("MUL", 34);
   }

   public int applyInteger(int x, int y) {
      return x * y;
   }

   public double applyDouble(double x, double y) {
      return x * y;
   }
}
