package edu.stanford.cs.sjslib.core;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;

class Array_size extends ArrayMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("Array.size", "");
      svm.pushInteger(this.getArray(svm, receiver).size());
   }
}
