package edu.stanford.cs.sjslib.core;

import edu.stanford.cs.svm.SVM;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class PauseListener implements ActionListener {
   private SVM svm;

   public PauseListener(SVM svm) {
      this.svm = svm;
   }

   public void actionPerformed(ActionEvent e) {
      this.svm.run();
   }
}
