package edu.stanford.cs.sjslib.util;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;

class Queue_clear extends QueueMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("Queue.clear", "");
      this.getQueue(svm, receiver).clear();
      svm.push(Value.UNDEFINED);
   }
}
