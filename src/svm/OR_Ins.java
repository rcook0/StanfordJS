package edu.stanford.cs.svm;

class OR_Ins extends LogicalOp {
   public OR_Ins() {
      super("OR", 82);
   }

   public int applyInteger(int x, int y) {
      return x | y;
   }
}
