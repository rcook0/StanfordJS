package edu.stanford.cs.svm;

import edu.stanford.cs.exp.Value;

class ARG_Ins extends SVMVarInstruction {
   public ARG_Ins() {
      super("ARG", 103);
   }

   public void execute(SVM svm, int addr) {
      SVMStackFrame cf = svm.getCurrentFrame();
      String name = svm.getString(addr);
      Value v = svm.pop();
      cf.declareVar(name);
      cf.setVar(name, v);
   }
}
