package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.graphics.GDimension;
import edu.stanford.cs.svm.SVM;
import edu.stanford.cs.svm.SVMMethod;

class GDimension_getWidth extends SVMMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("GDimension.getWidth", "");
      GDimension r = (GDimension)svm.pop().getValue();
      svm.pushDouble(r.getWidth());
   }
}
