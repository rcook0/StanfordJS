package edu.stanford.cs.svm;

class POPLOC_Ins extends SVMOffsetInstruction {
   public POPLOC_Ins() {
      super("POPLOC", 102);
   }

   public void execute(SVM svm, int addr) {
      svm.getCurrentFrame().setLocal(addr, svm.pop());
   }
}
