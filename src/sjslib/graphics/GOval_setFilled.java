package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;

class GOval_setFilled extends GOvalMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("GOval.setFilled", "B");
      boolean flag = svm.popBoolean();
      this.getGOval(svm, receiver).setFilled(flag);
      svm.push(Value.UNDEFINED);
   }
}
