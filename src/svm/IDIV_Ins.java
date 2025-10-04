package edu.stanford.cs.svm;

import edu.stanford.cs.exp.Value;

class IDIV_Ins extends SVMInstruction {
   public IDIV_Ins() {
      super("IDIV", 36);
   }

   public void execute(SVM svm, int addr) {
      Value rhs = svm.pop();
      Value lhs = svm.pop();
      if (lhs.isNumeric() && rhs.isNumeric()) {
         int result = (int)(lhs.getDoubleValue() / rhs.getDoubleValue());
         svm.pushInteger(result);
      } else {
         throw new RuntimeException("Illegal to apply " + this.getName() + " to " + lhs + " and " + rhs);
      }
   }
}
