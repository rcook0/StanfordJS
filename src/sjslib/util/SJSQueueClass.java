package edu.stanford.cs.sjslib.util;

import edu.stanford.cs.svm.SVMClass;

public class SJSQueueClass extends SVMClass {
   public SJSQueueClass() {
      this.defineMethod("new", new Queue_new());
      this.defineMethod("size", new Queue_size());
      this.defineMethod("isEmpty", new Queue_isEmpty());
      this.defineMethod("clear", new Queue_clear());
      this.defineMethod("enqueue", new Queue_enqueue());
      this.defineMethod("dequeue", new Queue_dequeue());
      this.defineMethod("add", new Queue_add());
      this.defineMethod("remove", new Queue_remove());
      this.defineMethod("peek", new Queue_peek());
   }
}
