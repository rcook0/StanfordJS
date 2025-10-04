package edu.stanford.cs.sjslib.util;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;

class Grid_inBounds extends GridMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("Grid.inBounds", "II");
      int nCols = svm.popInteger();
      int nRows = svm.popInteger();
      svm.pushBoolean(this.getGrid(svm, receiver).inBounds(nRows, nCols));
   }
}
