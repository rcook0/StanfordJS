package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.graphics.GObject;
import edu.stanford.cs.sjs.SJSGWindow;
import edu.stanford.cs.svm.SVM;

class GWindow_getElementAt extends GWindowMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("GWindow.remove", "DD");
      double y = svm.popDouble();
      double x = svm.popDouble();
      SJSGWindow gw = this.getGWindow(svm, receiver);
      GObject gobj = gw.getElementAt(x, y);
      if (gobj == null) {
         svm.push(Value.NULL);
      } else {
         svm.push(gw.getValueForGObject(gobj));
      }

   }
}
