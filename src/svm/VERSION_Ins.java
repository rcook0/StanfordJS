package edu.stanford.cs.svm;

class VERSION_Ins extends SVMOffsetInstruction {
   public VERSION_Ins() {
      super("VERSION", 1);
   }

   public void execute(SVM svm, int addr) {
      if (addr != 4) {
         throw new RuntimeException("Incompatible SVM version");
      }
   }
}
