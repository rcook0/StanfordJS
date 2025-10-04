package edu.stanford.cs.sjslib.core;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;
import edu.stanford.cs.svm.SVMMethod;

class Math_abs extends SVMMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("Math.abs", "D");
      Value v = svm.pop();
      switch(v.getType()) {
      case 68:
         svm.pushDouble(Math.abs(v.getDoubleValue()));
         break;
      case 73:
         svm.pushInteger(Math.abs(v.getIntegerValue()));
         break;
      default:
         throw new RuntimeException("Illegal type in abs");
      }

   }
}
