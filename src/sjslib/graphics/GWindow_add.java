package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.graphics.GObject;
import edu.stanford.cs.svm.SVM;

class GWindow_add extends GWindowMethod {
   public void execute(SVM svm, Value receiver) {
      if (svm.getArgumentCount() == 3) {
         svm.checkSignature("GWindow.add", "ODD");
         double y = svm.popDouble();
         double x = svm.popDouble();
         Value v = svm.pop();
         ((GObject)v.getValue()).setLocation(x, y);
         this.getGWindow(svm, receiver).add(v);
      } else {
         svm.checkSignature("GWindow.add", "O");
         Value v = svm.pop();
         this.getGWindow(svm, receiver).add(v);
      }

      svm.push(Value.UNDEFINED);
   }
}
