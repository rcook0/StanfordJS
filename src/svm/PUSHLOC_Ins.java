package edu.stanford.cs.svm;

class PUSHLOC_Ins extends SVMOffsetInstruction {
   public PUSHLOC_Ins() {
      super("PUSHLOC", 101);
   }

   public void execute(SVM svm, int addr) {
      svm.push(svm.getCurrentFrame().getLocal(addr));
   }
}
