package edu.stanford.cs.svm;

class NE_Ins extends RelationalOp {
   public NE_Ins() {
      super("NE", 49);
   }

   public boolean applyObject(Object x, Object y) {
      return x != y;
   }

   public boolean applyInteger(int x, int y) {
      return x != y;
   }

   public boolean applyDouble(double x, double y) {
      return x != y;
   }
}
