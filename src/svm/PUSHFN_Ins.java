package edu.stanford.cs.svm;

import edu.stanford.cs.exp.Value;

class PUSHFN_Ins extends SVMAddressInstruction {
   public PUSHFN_Ins() {
      super("PUSHFN", 19);
   }

   public void execute(SVM svm, int addr) {
      SVMStackFrame cf = svm.getCurrentFrame();
      int[] code = svm.getCode();
      SVMFunctionClosure closure = new SVMFunctionClosure(code, addr, cf);
      svm.push(Value.createObject(closure, "FunctionClosure"));
   }
}
