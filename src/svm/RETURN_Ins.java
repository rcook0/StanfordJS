package edu.stanford.cs.svm;

class RETURN_Ins extends SVMInstruction {
   public RETURN_Ins() {
      super("RETURN", 99);
   }

   public void execute(SVM svm, int addr) {
      svm.setPC(svm.getCurrentFrame().getReturnAddress());
      svm.popFrame();
      if (svm.getCurrentFrame() == null) {
         svm.setPC(-1);
      }

   }
}
