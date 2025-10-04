package edu.stanford.cs.sjslib.util;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;

class Grid_toString extends GridMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("Grid.toString", "");
      svm.pushString(this.getGrid(svm, receiver).toString());
   }
}
