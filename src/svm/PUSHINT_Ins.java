package edu.stanford.cs.svm;

class PUSHINT_Ins extends SVMAddressInstruction {
   public PUSHINT_Ins() {
      super("PUSHINT", 16);
   }

   public void execute(SVM svm, int addr) {
      svm.pushInteger(addr);
   }
}
