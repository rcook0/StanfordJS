package edu.stanford.cs.sjslib.core;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;
import edu.stanford.cs.svm.SVMArray;

class Array_reverse extends ArrayMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("Array.reverse", "");
      SVMArray array = this.getArray(svm, receiver);
      int lh = 0;

      for(int rh = array.size() - 1; lh < rh; --rh) {
         Value v = (Value)array.get(lh);
         array.set(lh, (Value)array.get(rh));
         array.set(rh, v);
         ++lh;
      }

      svm.push(receiver);
   }
}
