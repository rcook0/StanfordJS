package edu.stanford.cs.svm;

import edu.stanford.cs.exp.Value;

class ADD_Ins extends SVMInstruction {
   public ADD_Ins() {
      super("ADD", 32);
   }

   public void execute(SVM svm, int addr) {
      Value rhs = svm.pop();
      Value lhs = svm.pop();
      int lhsType = lhs.getType();
      int rhsType = rhs.getType();
      if (lhsType != 83 && rhsType != 83) {
         if (!lhs.isNumeric() || !rhs.isNumeric()) {
            throw new RuntimeException("Illegal to apply " + this.getName() + " to " + lhs + " and " + rhs);
         }

         if (lhsType == 73 && rhsType == 73) {
            int x = lhs.getIntegerValue();
            int y = rhs.getIntegerValue();
            svm.pushInteger(x + y);
         } else {
            double x = lhs.getDoubleValue();
            double y = rhs.getDoubleValue();
            svm.pushDouble(x + y);
         }
      } else {
         svm.pushString(svm.stringify(lhs) + svm.stringify(rhs));
      }

   }
}
