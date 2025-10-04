package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.java2js.JSColor;
import edu.stanford.cs.svm.SVM;
import java.awt.Color;

class GRoundRect_setFillColor extends GRoundRectMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("GRoundRect.setFillColor", "*");
      Value v = svm.pop();
      if (v.getType() == 83) {
         Color color = JSColor.decode((String)v.getValue());
         this.getGRoundRect(svm, receiver).setFillColor(color);
      } else {
         this.getGRoundRect(svm, receiver).setFillColor((Color)v.getValue());
      }

      svm.push(Value.UNDEFINED);
   }
}
