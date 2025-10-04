package edu.stanford.cs.sjslib.util;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;

class Grid_get extends GridMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("Grid.get", "II");
      int row = svm.popInteger();
      int col = svm.popInteger();
      svm.push(this.getGrid(svm, receiver).get(row, col));
   }
}
