package edu.stanford.cs.sjslib.util;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;

class Stack_push extends StackMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("Stack.push", "*");
      Value v = svm.pop();
      this.getStack(svm, receiver).push(v);
      svm.push(Value.UNDEFINED);
   }
}
