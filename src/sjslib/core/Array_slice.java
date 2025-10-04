package edu.stanford.cs.sjslib.core;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;
import edu.stanford.cs.svm.SVMArray;

class Array_slice extends ArrayMethod {
   public void execute(SVM svm, Value receiver) {
      int nArgs = svm.getArgumentCount();
      int end = -1;
      if (nArgs == 1) {
         svm.checkSignature("Array.slice", "I");
      } else {
         svm.checkSignature("Array.slice", "II");
         end = svm.popInteger();
      }

      int start = svm.popInteger();
      SVMArray array = this.getArray(svm, receiver);
      SVMArray result = new SVMArray();

      for(int i = start; i < end; ++i) {
         result.add(0, (Value)array.get(i));
      }

      svm.push(Value.createObject(result, "Array"));
   }
}
