package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.graphics.GRectangle;
import edu.stanford.cs.svm.SVM;

class GObject_getBounds extends GObjectMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("GObject.getBounds", "");
      GRectangle bounds = this.getGObject(svm, receiver).getBounds();
      svm.push(Value.createObject(bounds, "GRectangle"));
   }
}
