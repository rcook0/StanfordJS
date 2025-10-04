package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;

class GWindow_getCanvasHeight extends GWindowMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("GWindow.getCanvasHeight", "");
      svm.pushDouble((double)this.getGWindow(svm, receiver).getHeight());
   }
}
