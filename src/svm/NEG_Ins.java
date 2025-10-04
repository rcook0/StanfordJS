package edu.stanford.cs.svm;

import edu.stanford.cs.exp.Value;

class NEG_Ins extends SVMInstruction {
   public NEG_Ins() {
      super("NEG", 38);
   }

   public void execute(SVM svm, int addr) {
      Value rhs = svm.pop();
      if (!rhs.isNumeric()) {
         throw new RuntimeException("Illegal to apply " + this.getName() + " to " + rhs);
      } else {
         if (rhs.getType() == 73) {
            int x = rhs.getIntegerValue();
            svm.pushInteger(-x);
         } else {
            double x = rhs.getDoubleValue();
            svm.pushDouble(-x);
         }

      }
   }
}
