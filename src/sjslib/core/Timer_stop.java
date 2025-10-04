package edu.stanford.cs.sjslib.core;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;

class Timer_stop extends TimerMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("Timer.stop", "");
      this.getTimer(svm, receiver).stop();
      svm.push(Value.UNDEFINED);
   }
}
