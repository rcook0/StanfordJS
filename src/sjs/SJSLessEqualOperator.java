package edu.stanford.cs.sjs;

public class SJSLessEqualOperator extends SJSRelationalOperator {
   public boolean applyInteger(int x, int y) {
      return x <= y;
   }

   public boolean applyDouble(double x, double y) {
      return x <= y;
   }

   public int getInstructionCode() {
      return 51;
   }
}
