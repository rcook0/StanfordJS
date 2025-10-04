package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;

class GWindow_getCanvasWidth extends GWindowMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("GWindow.getCanvasWidth", "");
      svm.pushDouble((double)this.getGWindow(svm, receiver).getWidth());
   }
}
