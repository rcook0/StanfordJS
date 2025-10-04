package edu.stanford.cs.sjslib.core;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;

class Timer_start extends TimerMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("Timer.start", "");
      this.getTimer(svm, receiver).start();
      svm.push(Value.UNDEFINED);
   }
}
