package edu.stanford.cs.svm;

import edu.stanford.cs.exp.Value;

class VARGS_Ins extends SVMInstruction {
   public VARGS_Ins() {
      super("VARGS", 107);
   }

   public void execute(SVM svm, int addr) {
      SVMArray args = new SVMArray();
      int nArgs = svm.getArgumentCount();
      if (nArgs != -1) {
         for(int i = 0; i < nArgs; ++i) {
            args.add(0, svm.pop());
         }
      }

      svm.push(Value.createObject(args, "Array"));
   }
}
