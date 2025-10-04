package edu.stanford.cs.sjslib.core;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;
import edu.stanford.cs.svm.SVMMethod;
import java.awt.event.ActionListener;
import javax.swing.Timer;

class Timer_new extends SVMMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("Timer.new", "I");
      int delay = svm.popInteger();
      ActionListener l = (ActionListener)svm.getGlobal("canvas").getValue();
      svm.push(Value.createObject(new Timer(delay, l), "Timer"));
   }
}
