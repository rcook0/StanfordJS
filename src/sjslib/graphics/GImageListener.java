package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.svm.SVM;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class GImageListener implements ActionListener {
   private SVM svm;

   public GImageListener(SVM svm) {
      this.svm = svm;
   }

   public void actionPerformed(ActionEvent e) {
      this.svm.run();
   }
}
