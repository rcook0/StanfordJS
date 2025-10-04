package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;

class GObject_sendForward extends GObjectMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("GObject.sendForward", "");
      this.getGObject(svm, receiver).sendForward();
      svm.push(Value.UNDEFINED);
   }
}
