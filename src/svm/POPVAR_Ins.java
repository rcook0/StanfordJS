package edu.stanford.cs.svm;

class POPVAR_Ins extends SVMVarInstruction {
   public POPVAR_Ins() {
      super("POPVAR", 109);
   }

   public void execute(SVM svm, int addr) {
      SVMStackFrame cf = svm.getCurrentFrame();

      String name;
      for(name = svm.getString(addr); cf != null; cf = cf.getFrameLink()) {
         if (cf.isDeclared(name)) {
            cf.setVar(name, svm.pop());
            return;
         }
      }

      if (svm.isGlobal(name)) {
         svm.setGlobal(name, svm.pop());
      } else {
         throw new RuntimeException(name + " has not been declared");
      }
   }
}
