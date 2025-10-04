package edu.stanford.cs.sjslib.util;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;

class Stack_peek extends StackMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("Stack.peek", "");
      Stack stack = this.getStack(svm, receiver);
      if (stack.isEmpty()) {
         throw new RuntimeException("Stack is empty");
      } else {
         svm.push((Value)stack.peek());
      }
   }
}
