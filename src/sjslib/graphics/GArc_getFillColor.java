package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.java2js.JSColor;
import edu.stanford.cs.svm.SVM;
import java.awt.Color;

class GArc_getFillColor extends GArcMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("GArc.getFillColor", "");
      Color color = this.getGArc(svm, receiver).getFillColor();
      if (color == null) {
         svm.push(Value.NULL);
      } else {
         svm.pushString(JSColor.encode(color));
      }

   }
}
