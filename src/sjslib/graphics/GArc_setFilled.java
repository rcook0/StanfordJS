package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;

class GArc_setFilled extends GArcMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("GArc.setFilled", "B");
      boolean flag = svm.popBoolean();
      this.getGArc(svm, receiver).setFilled(flag);
      svm.push(Value.UNDEFINED);
   }
}
