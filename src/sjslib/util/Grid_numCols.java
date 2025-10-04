package edu.stanford.cs.sjslib.util;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;

class Grid_numCols extends GridMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("Grid.numCols", "");
      svm.pushInteger(this.getGrid(svm, receiver).numCols());
   }
}
