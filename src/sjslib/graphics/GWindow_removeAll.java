package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;

class GWindow_removeAll extends GWindowMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("GWindow.removeAll", "");
      this.getGWindow(svm, receiver).removeAll();
      svm.push(Value.UNDEFINED);
   }
}
