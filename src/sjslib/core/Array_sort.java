package edu.stanford.cs.sjslib.core;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.sjs.SJSComparator;
import edu.stanford.cs.svm.SVM;
import edu.stanford.cs.svm.SVMArray;
import java.util.Collections;

class Array_sort extends ArrayMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("Array.sort", "");
      SVMArray array = this.getArray(svm, receiver);
      Collections.sort(array, new SJSComparator());
      svm.push(receiver);
   }
}
