package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.java2js.JSColor;
import edu.stanford.cs.svm.SVM;
import java.awt.Color;

class GPolygon_getFillColor extends GPolygonMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("GPolygon.getFillColor", "");
      Color color = this.getGPolygon(svm, receiver).getFillColor();
      if (color == null) {
         svm.push(Value.NULL);
      } else {
         svm.pushString(JSColor.encode(color));
      }

   }
}
