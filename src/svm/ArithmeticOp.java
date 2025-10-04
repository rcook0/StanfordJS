package edu.stanford.cs.svm;

import edu.stanford.cs.exp.Value;

abstract class ArithmeticOp extends SVMInstruction {
   public ArithmeticOp(String name, int code) {
      super(name, code);
   }

   public void execute(SVM svm, int addr) {
      Value rhs = svm.pop();
      Value lhs = svm.pop();
      if (lhs.isNumeric() && rhs.isNumeric()) {
         if (lhs.getType() == 73 && rhs.getType() == 73) {
            int x = lhs.getIntegerValue();
            int y = rhs.getIntegerValue();
            svm.pushInteger(this.applyInteger(x, y));
         } else {
            double x = lhs.getDoubleValue();
            double y = rhs.getDoubleValue();
            svm.pushDouble(this.applyDouble(x, y));
         }

      } else {
         throw new RuntimeException("Illegal to apply " + this.getName() + " to " + lhs + " and " + rhs);
      }
   }

   public abstract int applyInteger(int var1, int var2);

   public abstract double applyDouble(double var1, double var3);
}
