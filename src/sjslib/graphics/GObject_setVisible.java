package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;

class GObject_setVisible extends GObjectMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("GObject.setVisible", "B");
      boolean flag = svm.popBoolean();
      this.getGObject(svm, receiver).setVisible(flag);
      svm.push(Value.UNDEFINED);
   }
}
