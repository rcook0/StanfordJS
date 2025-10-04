package edu.stanford.cs.sjslib.core;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.sjs.SJSVM;
import edu.stanford.cs.svm.SVM;
import edu.stanford.cs.svm.SVMMethod;

class Timer_clearInterval extends SVMMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("Timer.clearInterval", "I");
      ((SJSVM)svm).clearTimer(svm.popInteger());
      svm.push(Value.UNDEFINED);
   }
}
