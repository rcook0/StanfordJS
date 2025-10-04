package edu.stanford.cs.svm;

class TRY_Ins extends SVMAddressInstruction {
   public TRY_Ins() {
      super("TRY", 68);
   }

   public void execute(SVM svm, int addr) {
      svm.pushExceptionFrame(addr);
   }
}
