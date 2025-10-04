package edu.stanford.cs.svm;

class JUMP_Ins extends SVMAddressInstruction {
   public JUMP_Ins() {
      super("JUMP", 64);
   }

   public void execute(SVM svm, int addr) {
      svm.setPC(addr);
   }
}
