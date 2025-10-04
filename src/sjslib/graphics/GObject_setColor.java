package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.java2js.JSColor;
import edu.stanford.cs.svm.SVM;
import java.awt.Color;

class GObject_setColor extends GObjectMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("GObject.setColor", "*");
      Value v = svm.pop();
      if (v.getType() == 83) {
         Color color = JSColor.decode((String)v.getValue());
         this.getGObject(svm, receiver).setColor(color);
      } else {
         this.getGObject(svm, receiver).setColor((Color)v.getValue());
      }

      svm.push(Value.UNDEFINED);
   }
}
