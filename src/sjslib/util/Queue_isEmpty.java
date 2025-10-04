package edu.stanford.cs.sjslib.util;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;

class Queue_isEmpty extends QueueMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("Queue.isEmpty", "");
      svm.pushBoolean(this.getQueue(svm, receiver).isEmpty());
   }
}
