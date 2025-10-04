package edu.stanford.cs.svm;

class HALT_Ins extends SVMInstruction {
   public HALT_Ins() {
      super("HALT", 4);
   }

   public void execute(SVM svm, int addr) {
      svm.setPC(-1);
   }
}
