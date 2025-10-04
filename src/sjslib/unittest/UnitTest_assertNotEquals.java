package edu.stanford.cs.sjslib.unittest;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;
import edu.stanford.cs.svm.SVMMethod;
import edu.stanford.cs.unittest.UnitTest;

class UnitTest_assertNotEquals extends SVMMethod {
   public void execute(SVM svm, Value receiver) {
      UnitTest.setConsole(svm.getConsole());
      Value v2 = svm.pop();
      Value v1 = svm.pop();
      switch(svm.getArgumentCount()) {
      case 2:
         UnitTest.assertNotEquals(v1.getValue(), v2.getValue());
         break;
      case 3:
         UnitTest.assertNotEquals(svm.popString(), v1.getValue(), v2.getValue());
         break;
      default:
         throw new RuntimeException("Illegal call to assertNotEquals");
      }

   }
}
