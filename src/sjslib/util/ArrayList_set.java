package edu.stanford.cs.sjslib.util;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;

class ArrayList_set extends ArrayListMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("ArrayList.set", "I*");
      Value v = svm.pop();
      int k = svm.popInteger();
      this.getArrayList(svm, receiver).set(k, v);
      svm.push(Value.UNDEFINED);
   }
}
