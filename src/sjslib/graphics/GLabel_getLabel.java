package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;

class GLabel_getLabel extends GLabelMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("GLabel.getLabel", "");
      svm.pushString(this.getGLabel(svm, receiver).getLabel());
   }
}
