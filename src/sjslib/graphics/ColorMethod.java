package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;
import edu.stanford.cs.svm.SVMMethod;
import java.awt.Color;

abstract class ColorMethod extends SVMMethod {
   public Color getColor(SVM svm, Value receiver) {
      return receiver == null ? (Color)svm.pop().getValue() : (Color)receiver.getValue();
   }
}
