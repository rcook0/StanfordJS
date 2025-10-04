package edu.stanford.cs.sjslib.util;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;
import edu.stanford.cs.svm.SVMMethod;
import edu.stanford.cs.svm.SVMObject;

abstract class MapMethod extends SVMMethod {
   public SVMObject getMap(SVM svm, Value receiver) {
      return receiver == null ? (SVMObject)svm.pop().getValue() : (SVMObject)receiver.getValue();
   }
}
