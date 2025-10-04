package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;
import java.awt.Color;

class GObject_getJavaColor extends GObjectMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("GObject.getJavaColor", "");
      Color color = this.getGObject(svm, receiver).getColor();
      svm.push(Value.createObject(color, "Color"));
   }
}
