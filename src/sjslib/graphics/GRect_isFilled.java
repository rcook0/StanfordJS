package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;

class GRect_isFilled extends GRectMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("GRect.isFilled", "");
      svm.pushBoolean(this.getGRect(svm, receiver).isFilled());
   }
}
