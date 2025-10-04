package edu.stanford.cs.svm;

class DUP_Ins extends SVMInstruction {
   public DUP_Ins() {
      super("DUP", 21);
   }

   public void execute(SVM svm, int addr) {
      svm.push(svm.peekBack(0));
   }
}
