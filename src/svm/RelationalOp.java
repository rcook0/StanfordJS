package edu.stanford.cs.svm;

import edu.stanford.cs.exp.Value;

abstract class RelationalOp extends SVMInstruction {
   public RelationalOp(String name, int code) {
      super(name, code);
   }

   public void execute(SVM svm, int addr) {
      Value rhs = svm.pop();
      Value lhs = svm.pop();
      int lhsType = lhs.getType();
      int rhsType = rhs.getType();
      if (lhsType == 83 && rhsType == 83) {
         String s1 = lhs.getStringValue();
         String s2 = rhs.getStringValue();
         svm.pushBoolean(this.applyInteger(s1.compareTo(s2), 0));
      } else if (lhsType == 73 && rhsType == 73) {
         int x = lhs.getIntegerValue();
         int y = rhs.getIntegerValue();
         svm.pushBoolean(this.applyInteger(x, y));
      } else if (lhs.isNumeric() && rhs.isNumeric()) {
         double x = lhs.getDoubleValue();
         double y = rhs.getDoubleValue();
         svm.pushBoolean(this.applyDouble(x, y));
      } else {
         Object v1 = lhs.getValue();
         Object v2 = rhs.getValue();
         svm.pushBoolean(this.applyObject(v1, v2));
      }

   }

   public boolean applyObject(Object v1, Object v2) {
      throw new RuntimeException("Illegal object comparison");
   }

   public abstract boolean applyInteger(int var1, int var2);

   public abstract boolean applyDouble(double var1, double var3);
}
