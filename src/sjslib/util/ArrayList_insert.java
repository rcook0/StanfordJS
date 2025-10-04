package edu.stanford.cs.sjslib.util;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;

class ArrayList_insert extends ArrayListMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("ArrayList.insert", "I*");
      Value v = svm.pop();
      int k = svm.popInteger();
      this.getArrayList(svm, receiver).add(k, v);
      svm.push(Value.UNDEFINED);
   }
}
