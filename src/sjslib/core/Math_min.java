package edu.stanford.cs.sjslib.core;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;
import edu.stanford.cs.svm.SVMMethod;

class Math_min extends SVMMethod {
   public void execute(SVM svm, Value receiver) {
      int nArgs = svm.getArgumentCount();
      if (nArgs == -1) {
         nArgs = 2;
      }

      if (nArgs == 0) {
         throw new RuntimeException("min requires at least one argument");
      } else {
         Value min = svm.pop();

         for(int i = 1; i < nArgs; ++i) {
            Value v = svm.pop();
            if (min.getDoubleValue() > v.getDoubleValue()) {
               min = v;
            }
         }

         svm.push(min);
      }
   }
}
