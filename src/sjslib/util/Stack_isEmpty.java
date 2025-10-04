package edu.stanford.cs.sjslib.util;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;

class Stack_isEmpty extends StackMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("Stack.isEmpty", "");
      svm.pushBoolean(this.getStack(svm, receiver).isEmpty());
   }
}
