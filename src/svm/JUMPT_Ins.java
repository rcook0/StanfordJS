package edu.stanford.cs.svm;

class JUMPT_Ins extends SVMAddressInstruction {
   public JUMPT_Ins() {
      super("JUMPT", 65);
   }

   public void execute(SVM svm, int addr) {
      if (svm.popBoolean()) {
         svm.setPC(addr);
      }

   }
}
