package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;

class GRoundRect_isFilled extends GRoundRectMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("GRoundRect.isFilled", "");
      svm.pushBoolean(this.getGRoundRect(svm, receiver).isFilled());
   }
}
