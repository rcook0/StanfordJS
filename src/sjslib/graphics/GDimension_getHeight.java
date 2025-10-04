package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.graphics.GDimension;
import edu.stanford.cs.svm.SVM;
import edu.stanford.cs.svm.SVMMethod;

class GDimension_getHeight extends SVMMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("GDimension.getHeight", "");
      GDimension r = (GDimension)svm.pop().getValue();
      svm.pushDouble(r.getHeight());
   }
}
