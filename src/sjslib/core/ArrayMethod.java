package edu.stanford.cs.sjslib.core;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;
import edu.stanford.cs.svm.SVMArray;
import edu.stanford.cs.svm.SVMMethod;

abstract class ArrayMethod extends SVMMethod {
   public SVMArray getArray(SVM svm, Value receiver) {
      return receiver == null ? (SVMArray)svm.pop().getValue() : (SVMArray)receiver.getValue();
   }
}
