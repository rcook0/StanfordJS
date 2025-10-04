package edu.stanford.cs.svm;

class DISPATCH_Ins extends SVMInstruction {
   public DISPATCH_Ins() {
      super("DISPATCH", 67);
   }

   public void execute(SVM svm, int addr) {
      svm.pushFrame();
      svm.getCurrentFrame().setReturnAddress(svm.getPC());
      svm.setPC(svm.popInteger());
   }
}
