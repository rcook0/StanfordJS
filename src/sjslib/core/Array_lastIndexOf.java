package edu.stanford.cs.sjslib.core;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;
import edu.stanford.cs.svm.SVMArray;

class Array_lastIndexOf extends ArrayMethod {
   public void execute(SVM svm, Value receiver) {
      int nArgs = svm.getArgumentCount();
      int start = -1;
      if (nArgs == 1) {
         svm.checkSignature("Array.lastIndexOf", "*");
      } else {
         svm.checkSignature("Array.lastIndexOf", "*I");
         start = svm.popInteger();
      }

      Object element = svm.pop().getValue();
      SVMArray array = this.getArray(svm, receiver);

      for(int i = start; i >= 0; --i) {
         if (((Value)array.get(i)).getValue().equals(element)) {
            svm.pushInteger(i);
            return;
         }
      }

      svm.pushInteger(-1);
   }
}
