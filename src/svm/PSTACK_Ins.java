package edu.stanford.cs.svm;

class PSTACK_Ins extends SVMInstruction {
   public PSTACK_Ins() {
      super("PSTACK", 2);
   }

   public void execute(SVM svm, int addr) {
      svm.pstack();
   }
}
