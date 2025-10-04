package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;

class GWindow_clear extends GWindowMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("GWindow.clear", "");
      this.getGWindow(svm, receiver).clear();
      svm.push(Value.UNDEFINED);
   }
}
