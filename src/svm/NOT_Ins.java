package edu.stanford.cs.svm;

import edu.stanford.cs.exp.Value;

class NOT_Ins extends SVMInstruction {
   public NOT_Ins() {
      super("NOT", 80);
   }

   public void execute(SVM svm, int addr) {
      Value v = svm.pop();
      int type = v.getType();
      if (type == 66) {
         svm.pushBoolean(!v.getBooleanValue());
      } else {
         svm.pushInteger(~v.getIntegerValue());
      }

   }
}
