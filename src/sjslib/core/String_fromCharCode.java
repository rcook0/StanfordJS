package edu.stanford.cs.sjslib.core;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;

class String_fromCharCode extends StringMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("String.fromCharCode", "I");
      svm.pushString("" + (char)svm.popInteger());
   }
}
