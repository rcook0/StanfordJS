package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;

class GObject_sendBackward extends GObjectMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("GObject.sendBackward", "");
      this.getGObject(svm, receiver).sendBackward();
      svm.push(Value.UNDEFINED);
   }
}
