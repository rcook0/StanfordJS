package edu.stanford.cs.sjslib.util;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;

class Queue_size extends QueueMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("Queue.size", "");
      svm.pushInteger(this.getQueue(svm, receiver).size());
   }
}
