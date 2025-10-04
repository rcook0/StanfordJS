package edu.stanford.cs.sjslib.core;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;

class Timer_setActionCommand extends TimerMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("Timer.setActionCommand", "S");
      String cmd = svm.popString();
      this.getTimer(svm, receiver).setActionCommand(cmd);
      svm.push(Value.UNDEFINED);
   }
}
