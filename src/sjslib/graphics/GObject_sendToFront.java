package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;

class GObject_sendToFront extends GObjectMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("GObject.sendToFront", "");
      this.getGObject(svm, receiver).sendToFront();
      svm.push(Value.UNDEFINED);
   }
}
