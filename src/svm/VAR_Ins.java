package edu.stanford.cs.svm;

class VAR_Ins extends SVMVarInstruction {
   public VAR_Ins() {
      super("VAR", 104);
   }

   public void execute(SVM svm, int addr) {
      SVMStackFrame cf = svm.getCurrentFrame();
      String name = svm.getString(addr);
      cf.declareVar(name);
   }
}
