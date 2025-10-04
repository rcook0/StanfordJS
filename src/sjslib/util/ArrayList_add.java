package edu.stanford.cs.sjslib.util;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;

class ArrayList_add extends ArrayListMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("ArrayList.add", "*");
      Value v = svm.pop();
      this.getArrayList(svm, receiver).add(v);
      svm.push(Value.UNDEFINED);
   }
}
