package edu.stanford.cs.svm;

class ENDTRY_Ins extends SVMInstruction {
   public ENDTRY_Ins() {
      super("ENDTRY", 69);
   }

   public void execute(SVM svm, int addr) {
      svm.popExceptionFrame();
   }
}
