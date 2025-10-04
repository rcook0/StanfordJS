package edu.stanford.cs.svm;

class SUB_Ins extends ArithmeticOp {
   public SUB_Ins() {
      super("SUB", 33);
   }

   public int applyInteger(int x, int y) {
      return x - y;
   }

   public double applyDouble(double x, double y) {
      return x - y;
   }
}
