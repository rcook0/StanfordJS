package edu.stanford.cs.svm;

import edu.stanford.cs.exp.Value;

class DIV_Ins extends SVMInstruction {
   public DIV_Ins() {
      super("DIV", 35);
   }

   public void execute(SVM svm, int addr) {
      Value rhs = svm.pop();
      Value lhs = svm.pop();
      if (lhs.isNumeric() && rhs.isNumeric()) {
         if (lhs.getType() == 73 && rhs.getType() == 73) {
            int num = lhs.getIntegerValue();
            int den = rhs.getIntegerValue();
            if (den != 0 && num / den * den == num) {
               svm.pushInteger(num / den);
            } else {
               svm.pushDouble((double)num / (double)den);
            }
         } else {
            svm.pushDouble(lhs.getDoubleValue() / rhs.getDoubleValue());
         }

      } else {
         throw new RuntimeException("Illegal to apply " + this.getName() + " to " + lhs + " and " + rhs);
      }
   }
}
