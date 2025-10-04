package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.graphics.GLine;
import edu.stanford.cs.svm.SVM;
import edu.stanford.cs.svm.SVMMethod;

abstract class GLineMethod extends SVMMethod {
   public GLine getGLine(SVM svm, Value receiver) {
      return receiver == null ? (GLine)svm.pop().getValue() : (GLine)receiver.getValue();
   }
}
