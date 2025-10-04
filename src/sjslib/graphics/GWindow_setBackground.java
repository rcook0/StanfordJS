package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.java2js.JSColor;
import edu.stanford.cs.svm.SVM;
import java.awt.Color;

class GWindow_setBackground extends GWindowMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("GWindow.setBackground", "*");
      Value v = svm.pop();
      Color bg = null;
      if (v.getType() == 83) {
         bg = JSColor.decode((String)v.getValue());
      } else {
         bg = (Color)v.getValue();
      }

      this.getGWindow(svm, receiver).setBackground(bg);
      svm.push(Value.UNDEFINED);
   }
}
