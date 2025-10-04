package edu.stanford.cs.sjslib.core;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;
import edu.stanford.cs.svm.SVMMethod;
import javax.swing.Timer;

class Timer_pause extends SVMMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("Timer.pause", "I");
      Timer timer = new Timer(svm.popInteger(), new PauseListener(svm));
      timer.setRepeats(false);
      timer.start();
      svm.setState(6);
   }
}
