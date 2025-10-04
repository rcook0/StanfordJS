package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;
import edu.stanford.cs.svm.SVMConstant;
import java.awt.Color;

class Color_WHITE extends SVMConstant {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("Color.WHITE", "");
      svm.push(Value.createObject(Color.WHITE, "Color"));
   }
}
