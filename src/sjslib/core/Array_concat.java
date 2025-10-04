package edu.stanford.cs.sjslib.core;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;
import edu.stanford.cs.svm.SVMArray;

class Array_concat extends ArrayMethod {
   public void execute(SVM svm, Value receiver) {
      SVMArray args = new SVMArray();
      int nArgs = svm.getArgumentCount();

      for(int i = 0; i < nArgs; ++i) {
         args.add(0, svm.pop());
      }

      args.add(0, receiver == null ? svm.pop() : receiver);
      SVMArray result = new SVMArray();

      for(int i = 0; i < args.size(); ++i) {
         SVMArray array = (SVMArray)((Value)args.get(i)).getValue();

         for(int j = 0; i < array.size(); ++j) {
            result.add((Value)array.get(j));
         }
      }

      svm.push(Value.createObject(result, "Array"));
   }
}
