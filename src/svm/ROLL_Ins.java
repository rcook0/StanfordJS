package edu.stanford.cs.svm;

class ROLL_Ins extends SVMOffsetInstruction {
   public ROLL_Ins() {
      super("ROLL", 22);
   }

   public void execute(SVM svm, int addr) {
      svm.roll(addr);
   }
}
