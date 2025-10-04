package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.sjs.SJSGWindow;
import edu.stanford.cs.svm.SVM;

class GWindow_addEventListener extends GWindowMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("GWindow.addEventListener", "S*");
      SJSGWindow gw = this.getGWindow(svm, receiver);
      Value callback = svm.pop();
      String type = svm.popString();
      gw.addEventListener(type, callback);
      svm.push(Value.UNDEFINED);
   }
}
