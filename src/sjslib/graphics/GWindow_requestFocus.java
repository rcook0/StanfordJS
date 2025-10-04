package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.sjs.SJSGWindow;
import edu.stanford.cs.svm.SVM;

class GWindow_requestFocus extends GWindowMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("GWindow.requestFocus", "");
      SJSGWindow gw = this.getGWindow(svm, receiver);
      gw.requestFocus();
      svm.push(Value.UNDEFINED);
   }
}
