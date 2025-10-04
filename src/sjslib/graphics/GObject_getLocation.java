package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.graphics.GPoint;
import edu.stanford.cs.svm.SVM;

class GObject_getLocation extends GObjectMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("GObject.getLocation", "");
      GPoint pt = this.getGObject(svm, receiver).getLocation();
      svm.push(Value.createObject(pt, "GPoint"));
   }
}
