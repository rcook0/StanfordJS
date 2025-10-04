package edu.stanford.cs.sjslib.util;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;

class Grid_numRows extends GridMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("Grid.numRows", "");
      svm.pushInteger(this.getGrid(svm, receiver).numRows());
   }
}
