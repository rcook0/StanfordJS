package edu.stanford.cs.sjslib.core;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;
import edu.stanford.cs.svm.SVMArray;

class Array_push extends ArrayMethod {
   public void execute(SVM svm, Value receiver) {
      SVMArray args = new SVMArray();
      int nArgs = svm.getArgumentCount();

      for(int i = 0; i < nArgs; ++i) {
         args.add(0, svm.pop());
      }

      SVMArray array = this.getArray(svm, receiver);

      for(int i = 0; i < args.size(); ++i) {
         array.add((Value)args.get(i));
      }

      svm.pushInteger(array.size());
   }
}
