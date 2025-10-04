package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;

class GArc_getStartAngle extends GArcMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("GArc.getStartAngle", "");
      svm.pushDouble(this.getGArc(svm, receiver).getStartAngle());
   }
}
