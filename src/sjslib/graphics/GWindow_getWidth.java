package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.sjs.SJSGWindow;
import edu.stanford.cs.svm.SVM;

class GWindow_getWidth extends GWindowMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("GWindow.getWidth", "");
      SJSGWindow gw = this.getGWindow(svm, receiver);
      svm.pushDouble((double)gw.getWidth());
   }
}
