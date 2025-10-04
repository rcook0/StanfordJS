package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;

class GObject_isVisible extends GObjectMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("GObject.isVisible", "");
      svm.pushBoolean(this.getGObject(svm, receiver).isVisible());
   }
}
