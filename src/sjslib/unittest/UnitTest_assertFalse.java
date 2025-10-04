package edu.stanford.cs.sjslib.unittest;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;
import edu.stanford.cs.svm.SVMMethod;
import edu.stanford.cs.unittest.UnitTest;

class UnitTest_assertFalse extends SVMMethod {
   public void execute(SVM svm, Value receiver) {
      UnitTest.setConsole(svm.getConsole());
      boolean flag = svm.popBoolean();
      switch(svm.getArgumentCount()) {
      case 1:
         UnitTest.assertFalse(flag);
         break;
      case 2:
         UnitTest.assertFalse(svm.popString(), flag);
         break;
      default:
         throw new RuntimeException("Illegal call to assertFalse");
      }

   }
}
