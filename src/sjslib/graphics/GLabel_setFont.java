package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;

class GLabel_setFont extends GLabelMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("GLabel.setFont", "S");
      String str = svm.popString();
      this.getGLabel(svm, receiver).setFont(str);
      svm.push(Value.UNDEFINED);
   }
}
