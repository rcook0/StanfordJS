package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;

class GPolygon_setFilled extends GPolygonMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("GPolygon.setFilled", "B");
      boolean flag = svm.popBoolean();
      this.getGPolygon(svm, receiver).setFilled(flag);
      svm.push(Value.UNDEFINED);
   }
}
