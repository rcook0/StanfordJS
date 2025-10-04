package edu.stanford.cs.sjslib.unittest;

import edu.stanford.cs.svm.SVMClass;

public class SJSUnitTestClass extends SVMClass {
   public SJSUnitTestClass() {
      this.defineMethod("assertTrue", new UnitTest_assertTrue());
      this.defineMethod("assertFalse", new UnitTest_assertFalse());
      this.defineMethod("assertEquals", new UnitTest_assertEquals());
      this.defineMethod("assertNotEquals", new UnitTest_assertNotEquals());
      this.defineMethod("resetErrorCount", new UnitTest_resetErrorCount());
      this.defineMethod("getErrorCount", new UnitTest_getErrorCount());
   }
}
