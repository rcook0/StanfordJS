package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.java2js.JSColor;
import edu.stanford.cs.svm.SVM;

class GObject_getColor extends GObjectMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("GObject.getColor", "");
      svm.pushString(JSColor.encode(this.getGObject(svm, receiver).getColor()));
   }
}
