package edu.stanford.cs.sjslib.util;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;
import edu.stanford.cs.svm.SVMMethod;

abstract class QueueMethod extends SVMMethod {
   public Queue getQueue(SVM svm, Value receiver) {
      return receiver == null ? (Queue)svm.pop().getValue() : (Queue)receiver.getValue();
   }
}
