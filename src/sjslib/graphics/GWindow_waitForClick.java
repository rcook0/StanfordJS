package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.sjs.SJSGWindow;
import edu.stanford.cs.svm.SVM;

class GWindow_waitForClick extends GWindowMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("GWindow.waitForClick", "");
      SJSGWindow gw = this.getGWindow(svm, receiver);
      gw.enableMouseListener();
      gw.repaint();
      gw.setState(1);
      svm.setState(6);
   }
}
