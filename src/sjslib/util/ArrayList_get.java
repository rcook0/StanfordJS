package edu.stanford.cs.sjslib.util;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;

class ArrayList_get extends ArrayListMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("ArrayList.get", "I");
      int k = svm.popInteger();
      svm.push((Value)this.getArrayList(svm, receiver).get(k));
   }
}
