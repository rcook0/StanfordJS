package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.java2js.JSColor;
import edu.stanford.cs.svm.SVM;
import java.awt.Color;

class GRoundRect_getFillColor extends GRoundRectMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("GRoundRect.getFillColor", "");
      Color color = this.getGRoundRect(svm, receiver).getFillColor();
      if (color == null) {
         svm.push(Value.NULL);
      } else {
         svm.pushString(JSColor.encode(color));
      }

   }
}
