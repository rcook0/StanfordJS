package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;

class GLabel_getAscent extends GLabelMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("GLabel.getAscent", "");
      svm.pushDouble(this.getGLabel(svm, receiver).getAscent());
   }
}
