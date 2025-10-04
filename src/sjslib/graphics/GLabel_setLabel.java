package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;

class GLabel_setLabel extends GLabelMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("GLabel.setLabel", "S");
      String str = svm.popString();
      this.getGLabel(svm, receiver).setLabel(str);
      svm.push(Value.UNDEFINED);
   }
}
