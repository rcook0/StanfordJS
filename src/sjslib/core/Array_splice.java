package edu.stanford.cs.sjslib.core;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;
import edu.stanford.cs.svm.SVMArray;

class Array_splice extends ArrayMethod {
   public void execute(SVM svm, Value receiver) {
      SVMArray args = new SVMArray();
      int nArgs = svm.getArgumentCount();

      int deleteCount;
      for(deleteCount = 0; deleteCount < nArgs - 2; ++deleteCount) {
         args.add(0, svm.pop());
      }

      deleteCount = svm.popInteger();
      int index = svm.popInteger();
      SVMArray array = this.getArray(svm, receiver);
      SVMArray result = new SVMArray();

      int i;
      for(i = 0; i < deleteCount; ++i) {
         result.add((Value)array.get(index));
         array.remove(index);
      }

      for(i = 0; i < nArgs - 2; ++i) {
         array.add(index + 1, (Value)args.get(i));
      }

      svm.push(Value.createObject(result, "Array"));
   }
}
