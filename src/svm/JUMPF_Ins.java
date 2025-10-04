package edu.stanford.cs.svm;

class JUMPF_Ins extends SVMAddressInstruction {
   public JUMPF_Ins() {
      super("JUMPF", 66);
   }

   public void execute(SVM svm, int addr) {
      if (!svm.popBoolean()) {
         svm.setPC(addr);
      }

   }
}
