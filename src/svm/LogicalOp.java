package edu.stanford.cs.svm;

import edu.stanford.cs.exp.Value;

abstract class LogicalOp extends SVMInstruction {
   public LogicalOp(String name, int code) {
      super(name, code);
   }

   public void execute(SVM svm, int addr) {
      Value rhs = svm.pop();
      Value lhs = svm.pop();
      int lhsType = lhs.getType();
      int rhsType = rhs.getType();
      int x;
      int y;
      if (lhsType == 66 && rhsType == 66) {
         x = lhs.getBooleanValue() ? -1 : 0;
         y = rhs.getBooleanValue() ? -1 : 0;
         svm.pushBoolean(this.applyInteger(x, y) != 0);
      } else {
         x = lhs.getIntegerValue();
         y = rhs.getIntegerValue();
         svm.pushInteger(this.applyInteger(x, y));
      }

   }

   public abstract int applyInteger(int var1, int var2);
}
