package edu.stanford.cs.sjslib.util;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;

class ArrayList_clear extends ArrayListMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("ArrayList.clear", "");
      this.getArrayList(svm, receiver).clear();
      svm.push(Value.UNDEFINED);
   }
}
