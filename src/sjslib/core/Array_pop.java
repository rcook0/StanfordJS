package edu.stanford.cs.sjslib.core;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;
import edu.stanford.cs.svm.SVMArray;

class Array_pop extends ArrayMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("Array.pop", "");
      SVMArray array = this.getArray(svm, receiver);
      int n = array.size();
      if (n == 0) {
         svm.push(Value.UNDEFINED);
      } else {
         Value v = (Value)array.get(n - 1);
         array.remove(n - 1);
         svm.push(v);
      }

   }
}
