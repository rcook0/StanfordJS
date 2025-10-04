package edu.stanford.cs.sjslib.util;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;

class Queue_remove extends QueueMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("Queue.remove", "");
      Queue queue = this.getQueue(svm, receiver);
      if (queue.isEmpty()) {
         throw new RuntimeException("Queue is empty");
      } else {
         svm.push((Value)queue.remove());
      }
   }
}
