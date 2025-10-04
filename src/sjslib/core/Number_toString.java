package edu.stanford.cs.sjslib.core;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;
import edu.stanford.cs.svm.SVMMethod;

class Number_toString extends SVMMethod {
   public void execute(SVM svm, Value receiver) {
      if (svm.getArgumentCount() == 0) {
         svm.checkSignature("Number.toString", "");
         if (receiver.isIntegral()) {
            svm.pushString("" + receiver.getIntegerValue());
         } else {
            svm.pushString("" + receiver.getDoubleValue());
         }
      } else {
         svm.checkSignature("Number.toString", "D");
         int base = svm.popInteger();
         if (receiver.isIntegral()) {
            svm.pushString(Integer.toString(receiver.getIntegerValue(), base));
         } else {
            svm.pushString("" + receiver.getDoubleValue());
         }
      }

   }
}
