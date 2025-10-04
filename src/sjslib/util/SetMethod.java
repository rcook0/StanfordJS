package edu.stanford.cs.sjslib.util;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;
import edu.stanford.cs.svm.SVMMethod;

abstract class SetMethod extends SVMMethod {
   public Set getSet(SVM svm, Value receiver) {
      return receiver == null ? (Set)svm.pop().getValue() : (Set)receiver.getValue();
   }
}
