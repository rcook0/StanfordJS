package edu.stanford.cs.svm;

class STMT_Ins extends SVMOffsetInstruction {
   public STMT_Ins() {
      super("STMT", 3);
   }

   public void execute(SVM svm, int addr) {
      svm.setStatementOffset(addr);
      svm.restoreStackBase();
   }
}
