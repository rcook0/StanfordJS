package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.sjs.SJSGWindow;
import edu.stanford.cs.svm.SVM;

class GWindow_waitForEvent extends GWindowMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("GWindow.waitForEvent", "");
      SJSGWindow gw = this.getGWindow(svm, receiver);
      gw.enableMouseListener();
      gw.enableMouseMotionListener();
      gw.enableKeyListener();
      gw.repaint();
      gw.setState(2);
      svm.setState(6);
   }
}
