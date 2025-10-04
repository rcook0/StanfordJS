package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.java2js.JSColor;
import edu.stanford.cs.svm.SVM;
import java.awt.Color;

class GRect_setFillColor extends GRectMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("GRect.setFillColor", "*");
      Value v = svm.pop();
      if (v.getType() == 83) {
         Color color = JSColor.decode((String)v.getValue());
         this.getGRect(svm, receiver).setFillColor(color);
      } else {
         this.getGRect(svm, receiver).setFillColor((Color)v.getValue());
      }

      svm.push(Value.UNDEFINED);
   }
}
