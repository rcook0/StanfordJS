package edu.stanford.cs.svm;

class PUSHSTR_Ins extends SVMStringInstruction {
   public PUSHSTR_Ins() {
      super("PUSHSTR", 18);
   }

   public void execute(SVM svm, int addr) {
      svm.pushString(svm.getString(addr));
   }
}
