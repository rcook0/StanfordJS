package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;

class GLabel_getDescent extends GLabelMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("GLabel.getDescent", "");
      svm.pushDouble(this.getGLabel(svm, receiver).getDescent());
   }
}
