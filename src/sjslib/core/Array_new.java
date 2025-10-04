package edu.stanford.cs.sjslib.core;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;
import edu.stanford.cs.svm.SVMArray;
import edu.stanford.cs.svm.SVMMethod;

class Array_new extends SVMMethod {
   public void execute(SVM svm, Value receiver) {
      SVMArray array = new SVMArray();
      int nArgs = svm.getArgumentCount();
      if (nArgs < 1) {
         svm.checkSignature("Array.new", "");
      } else {
         Value init = Value.UNDEFINED;
         if (nArgs == 1) {
            svm.checkSignature("Array.new", "I");
         } else {
            svm.checkSignature("Array.new", "I*");
            init = svm.pop();
         }

         int n = svm.popInteger();

         for(int i = 0; i < n; ++i) {
            array.add(init);
         }
      }

      svm.push(Value.createObject(array, "Array"));
   }
}
