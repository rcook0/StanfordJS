package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.graphics.GDimension;
import edu.stanford.cs.svm.SVM;

class GObject_getSize extends GObjectMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("GObject.getSize", "");
      GDimension size = this.getGObject(svm, receiver).getSize();
      svm.push(Value.createObject(size, "GDimension"));
   }
}
