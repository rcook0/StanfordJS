package edu.stanford.cs.sjslib.unittest;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;
import edu.stanford.cs.svm.SVMMethod;
import edu.stanford.cs.unittest.UnitTest;

class UnitTest_getErrorCount extends SVMMethod {
   public void execute(SVM svm, Value receiver) {
      svm.pushInteger(UnitTest.getErrorCount());
   }
}
