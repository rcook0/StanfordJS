package edu.stanford.cs.sjslib.util;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;

class Queue_enqueue extends QueueMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("Queue.enqueue", "*");
      Value v = svm.pop();
      this.getQueue(svm, receiver).add(v);
      svm.push(Value.UNDEFINED);
   }
}
