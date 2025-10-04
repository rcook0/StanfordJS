package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;

class GObject_movePolar extends GObjectMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("GObject.movePolar", "DD");
      double theta = svm.popDouble();
      double r = svm.popDouble();
      this.getGObject(svm, receiver).movePolar(r, theta);
      svm.push(Value.UNDEFINED);
   }
}
