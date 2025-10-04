package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;

class GArc_getSweepAngle extends GArcMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("GArc.getSweepAngle", "");
      svm.pushDouble(this.getGArc(svm, receiver).getSweepAngle());
   }
}
