package edu.stanford.cs.sjslib.util;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;
import edu.stanford.cs.svm.SVMArray;
import edu.stanford.cs.svm.SVMMethod;

class ArrayList_new extends SVMMethod {
   public void execute(SVM svm, Value receiver) {
      SVMArray array = new SVMArray();
      svm.checkSignature("ArrayList.new", "");
      svm.push(Value.createObject(array, "ArrayList"));
   }
}
