package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;

class GObject_getLineWidth extends GObjectMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("GObject.getLineWidth", "");
      svm.pushDouble(this.getGObject(svm, receiver).getLineWidth());
   }
}
