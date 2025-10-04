package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;

class Color_getGreen extends ColorMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("Color.getGreen", "");
      svm.pushInteger(this.getColor(svm, receiver).getGreen());
   }
}
