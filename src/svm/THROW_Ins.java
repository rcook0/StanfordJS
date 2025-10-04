package edu.stanford.cs.svm;

import edu.stanford.cs.exp.Value;

class THROW_Ins extends SVMInstruction {
   public THROW_Ins() {
      super("THROW", 70);
   }

   public void execute(SVM svm, int addr) {
      Value v = svm.pop();
      svm.throwException(new RuntimeException(v.toString()), v);
   }
}
