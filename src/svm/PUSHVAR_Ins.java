package edu.stanford.cs.svm;

class PUSHVAR_Ins extends SVMVarInstruction {
   public PUSHVAR_Ins() {
      super("PUSHVAR", 108);
   }

   public void execute(SVM svm, int addr) {
      SVMStackFrame cf = svm.getCurrentFrame();

      String name;
      for(name = svm.getString(addr); cf != null; cf = cf.getFrameLink()) {
         if (cf.isDeclared(name)) {
            svm.push(cf.getVar(name));
            return;
         }
      }

      if (svm.isGlobal(name)) {
         svm.push(svm.getGlobal(name));
      } else {
         throw new RuntimeException(name + " has not been declared");
      }
   }
}
