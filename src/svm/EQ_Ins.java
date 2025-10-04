package edu.stanford.cs.svm;

class EQ_Ins extends RelationalOp {
   public EQ_Ins() {
      super("EQ", 48);
   }

   public boolean applyObject(Object x, Object y) {
      return x == y;
   }

   public boolean applyInteger(int x, int y) {
      return x == y;
   }

   public boolean applyDouble(double x, double y) {
      return x == y;
   }
}
