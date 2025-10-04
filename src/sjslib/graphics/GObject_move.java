package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;

class GObject_move extends GObjectMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("GObject.move", "DD");
      double dy = svm.popDouble();
      double dx = svm.popDouble();
      this.getGObject(svm, receiver).move(dx, dy);
      svm.push(Value.UNDEFINED);
   }
}
