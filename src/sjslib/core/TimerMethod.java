package edu.stanford.cs.sjslib.core;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;
import edu.stanford.cs.svm.SVMMethod;
import javax.swing.Timer;

abstract class TimerMethod extends SVMMethod {
   public Timer getTimer(SVM svm, Value receiver) {
      return receiver == null ? (Timer)svm.pop().getValue() : (Timer)receiver.getValue();
   }
}
