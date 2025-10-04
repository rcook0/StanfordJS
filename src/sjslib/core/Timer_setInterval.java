package edu.stanford.cs.sjslib.core;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.sjs.SJSVM;
import edu.stanford.cs.svm.SVM;
import edu.stanford.cs.svm.SVMMethod;
import javax.swing.Timer;

class Timer_setInterval extends SVMMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("Timer.setInterval", "*I");
      int delay = svm.popInteger();
      Value fn = svm.pop();
      Timer timer = new Timer(delay, new TimeoutListener(svm, fn));
      timer.setRepeats(true);
      timer.start();
      svm.pushInteger(((SJSVM)svm).addTimer(timer));
   }
}
