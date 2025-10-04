package edu.stanford.cs.sjslib.util;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;

class Grid_resize extends GridMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("Grid.resize", "II");
      int nCols = svm.popInteger();
      int nRows = svm.popInteger();
      this.getGrid(svm, receiver).resize(nRows, nCols);
      svm.push(Value.UNDEFINED);
   }
}
