package edu.stanford.cs.sjslib.core;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;
import edu.stanford.cs.svm.SVMArray;
import edu.stanford.cs.svm.SVMMethod;

class Array_create extends SVMMethod {
   public void execute(SVM svm, Value receiver) {
      SVMArray array = new SVMArray();
      int nArgs = svm.getArgumentCount();

      for(int i = 0; i < nArgs; ++i) {
         array.add(0, svm.pop());
      }

      svm.push(Value.createObject(array, "Array"));
   }
}
