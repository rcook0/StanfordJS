package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;

class GOval_isFilled extends GOvalMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("GOval.isFilled", "");
      svm.pushBoolean(this.getGOval(svm, receiver).isFilled());
   }
}
