package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.graphics.GObject;
import edu.stanford.cs.svm.SVM;
import edu.stanford.cs.svm.SVMMethod;

abstract class GObjectMethod extends SVMMethod {
   public GObject getGObject(SVM svm, Value receiver) {
      return receiver == null ? (GObject)svm.pop().getValue() : (GObject)receiver.getValue();
   }
}
