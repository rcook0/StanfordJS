package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;

class GArc_setSweepAngle extends GArcMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("GArc.setSweepAngle", "D");
      double angle = svm.popDouble();
      this.getGArc(svm, receiver).setSweepAngle(angle);
      svm.push(Value.UNDEFINED);
   }
}
