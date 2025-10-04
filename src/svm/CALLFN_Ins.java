package edu.stanford.cs.svm;

class CALLFN_Ins extends SVMInstruction {
   public CALLFN_Ins() {
      super("CALLFN", 98);
   }

   public void execute(SVM svm, int addr) {
      svm.call(svm.getNARGSCount());
   }
}
