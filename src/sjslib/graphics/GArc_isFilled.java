package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;

class GArc_isFilled extends GArcMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("GArc.isFilled", "");
      svm.pushBoolean(this.getGArc(svm, receiver).isFilled());
   }
}
