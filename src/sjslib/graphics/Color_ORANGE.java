package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;
import edu.stanford.cs.svm.SVMConstant;
import java.awt.Color;

class Color_ORANGE extends SVMConstant {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("Color.ORANGE", "");
      svm.push(Value.createObject(Color.ORANGE, "Color"));
   }
}
