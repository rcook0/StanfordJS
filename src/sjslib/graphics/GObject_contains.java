package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.graphics.GPoint;
import edu.stanford.cs.svm.SVM;

class GObject_contains extends GObjectMethod {
   public void execute(SVM svm, Value receiver) {
      if (svm.getArgumentCount() == 1) {
         svm.checkSignature("GObject.contains", "O");
         GPoint pt = (GPoint)svm.pop().getValue();
         svm.pushBoolean(this.getGObject(svm, receiver).contains(pt.getX(), pt.getY()));
      } else {
         svm.checkSignature("GObject.contains", "DD");
         double y = svm.popDouble();
         double x = svm.popDouble();
         svm.pushBoolean(this.getGObject(svm, receiver).contains(x, y));
      }

   }
}
