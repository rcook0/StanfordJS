package edu.stanford.cs.svm;

class PARAMS_Ins extends SVMOffsetInstruction {
   public PARAMS_Ins() {
      super("PARAMS", 105);
   }

   public void execute(SVM svm, int addr) {
      int nArgs = svm.getArgumentCount();
      if (nArgs != -1) {
         svm.checkArgumentCount(nArgs, addr);
         svm.setStackBase(nArgs);
      }

   }
}
