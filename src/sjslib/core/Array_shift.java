package edu.stanford.cs.sjslib.core;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;
import edu.stanford.cs.svm.SVMArray;

class Array_shift extends ArrayMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("Array.shift", "");
      SVMArray array = this.getArray(svm, receiver);
      if (array.isEmpty()) {
         svm.push(Value.UNDEFINED);
      } else {
         Value v = (Value)array.get(0);
         array.remove(0);
         svm.push(v);
      }

   }
}
