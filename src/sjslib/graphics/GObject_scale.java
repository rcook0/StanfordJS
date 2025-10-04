package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;

class GObject_scale extends GObjectMethod {
   public void execute(SVM svm, Value receiver) {
      double sy;
      if (svm.getArgumentCount() == 2) {
         svm.checkSignature("GObject.scale", "DD");
         sy = svm.popDouble();
         double sx = svm.popDouble();
         this.getGObject(svm, receiver).scale(sx, sy);
      } else {
         svm.checkSignature("GObject.scale", "D");
         sy = svm.popDouble();
         this.getGObject(svm, receiver).scale(sy);
      }

      svm.push(Value.UNDEFINED);
   }
}
