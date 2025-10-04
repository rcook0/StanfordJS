package edu.stanford.cs.sjslib.core;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.sjs.SJSVM;
import edu.stanford.cs.svm.SVM;
import edu.stanford.cs.svm.SVMEventClosure;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

class TimeoutListener implements ActionListener {
   private SVM svm;
   private Value fn;

   public TimeoutListener(SVM svm, Value fn) {
      this.svm = svm;
      this.fn = fn;
   }

   public void actionPerformed(ActionEvent e) {
      Timer timer = (Timer)e.getSource();
      if (!timer.isRepeats()) {
         ((SJSVM)this.svm).removeTimer(timer);
      }

      this.svm.postEvent(new SVMEventClosure(this.fn));
   }
}
