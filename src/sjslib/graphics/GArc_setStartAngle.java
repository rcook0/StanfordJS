package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;

class GArc_setStartAngle extends GArcMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("GArc.setStartAngle", "D");
      double angle = svm.popDouble();
      this.getGArc(svm, receiver).setStartAngle(angle);
      svm.push(Value.UNDEFINED);
   }
}
