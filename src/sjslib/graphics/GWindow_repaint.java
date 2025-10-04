package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.sjs.SJSGWindow;
import edu.stanford.cs.svm.SVM;

class GWindow_repaint extends GWindowMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("GWindow.repaint", "");
      SJSGWindow gw = this.getGWindow(svm, receiver);
      if (gw != null) {
         gw.repaint();
      }

      svm.push(Value.UNDEFINED);
   }
}
