package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.graphics.GCompound;
import edu.stanford.cs.svm.SVM;
import edu.stanford.cs.svm.SVMMethod;

abstract class GCompoundMethod extends SVMMethod {
   public GCompound getGCompound(SVM svm, Value receiver) {
      return receiver == null ? (GCompound)svm.pop().getValue() : (GCompound)receiver.getValue();
   }
}
