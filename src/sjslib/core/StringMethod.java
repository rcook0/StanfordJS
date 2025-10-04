package edu.stanford.cs.sjslib.core;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;
import edu.stanford.cs.svm.SVMMethod;

abstract class StringMethod extends SVMMethod {
   public String getString(SVM svm, Value receiver) {
      return receiver == null ? svm.popString() : receiver.getStringValue();
   }
}
