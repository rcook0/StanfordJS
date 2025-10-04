package edu.stanford.cs.svm;

class END_Ins extends SVMInstruction {
   public END_Ins() {
      super("END", 0);
   }

   public void execute(SVM svm, int addr) {
      svm.setPC(-1);
   }
}
