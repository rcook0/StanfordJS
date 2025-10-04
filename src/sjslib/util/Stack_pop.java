package edu.stanford.cs.sjslib.util;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;

class Stack_pop extends StackMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("Stack.pop", "");
      Stack stack = this.getStack(svm, receiver);
      if (stack.isEmpty()) {
         throw new RuntimeException("Stack is empty");
      } else {
         svm.push((Value)stack.pop());
      }
   }
}
