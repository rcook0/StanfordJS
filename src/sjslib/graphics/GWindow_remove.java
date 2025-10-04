package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.graphics.GObject;
import edu.stanford.cs.svm.SVM;

class GWindow_remove extends GWindowMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("GWindow.remove", "O");
      Value v = svm.pop();
      this.getGWindow(svm, receiver).remove((GObject)v.getValue());
      svm.push(Value.UNDEFINED);
   }
}
