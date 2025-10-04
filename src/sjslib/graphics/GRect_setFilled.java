package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;

class GRect_setFilled extends GRectMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("GRect.setFilled", "B");
      boolean flag = svm.popBoolean();
      this.getGRect(svm, receiver).setFilled(flag);
      svm.push(Value.UNDEFINED);
   }
}
