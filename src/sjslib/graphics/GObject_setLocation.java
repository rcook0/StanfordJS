package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.graphics.GPoint;
import edu.stanford.cs.svm.SVM;

class GObject_setLocation extends GObjectMethod {
   public void execute(SVM svm, Value receiver) {
      if (svm.getArgumentCount() == 1) {
         svm.checkSignature("GObject.setLocation", "O");
         GPoint pt = (GPoint)svm.pop().getValue();
         this.getGObject(svm, receiver).setLocation(pt.getX(), pt.getY());
      } else {
         svm.checkSignature("GObject.setLocation", "DD");
         double y = svm.popDouble();
         double x = svm.popDouble();
         this.getGObject(svm, receiver).setLocation(x, y);
      }

   }
}
