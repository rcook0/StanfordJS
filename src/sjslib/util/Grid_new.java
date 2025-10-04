package edu.stanford.cs.sjslib.util;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;
import edu.stanford.cs.svm.SVMMethod;

class Grid_new extends SVMMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("Grid.new", "II");
      int nCols = svm.popInteger();
      int nRows = svm.popInteger();
      svm.push(Value.createObject(new Grid(nRows, nCols), "Grid"));
   }
}
