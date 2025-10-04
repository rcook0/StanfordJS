package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;

class GRoundRect_setFilled extends GRoundRectMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("GRoundRect.setFilled", "B");
      boolean flag = svm.popBoolean();
      this.getGRoundRect(svm, receiver).setFilled(flag);
      svm.push(Value.UNDEFINED);
   }
}
