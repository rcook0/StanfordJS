package edu.stanford.cs.sjslib.core;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;

class Timer_setRepeats extends TimerMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("Timer.setRepeats", "B");
      boolean flag = svm.popBoolean();
      this.getTimer(svm, receiver).setRepeats(flag);
      svm.push(Value.UNDEFINED);
   }
}
