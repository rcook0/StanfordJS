package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;

class GObject_sendToBack extends GObjectMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("GObject.sendToBack", "");
      this.getGObject(svm, receiver).sendToBack();
      svm.push(Value.UNDEFINED);
   }
}
