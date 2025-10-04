package edu.stanford.cs.sjslib.util;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;
import edu.stanford.cs.svm.SVMMethod;

abstract class StackMethod extends SVMMethod {
   public Stack getStack(SVM svm, Value receiver) {
      return receiver == null ? (Stack)svm.pop().getValue() : (Stack)receiver.getValue();
   }
}
