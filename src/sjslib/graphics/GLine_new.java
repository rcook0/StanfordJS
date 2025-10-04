package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.graphics.GLine;
import edu.stanford.cs.svm.SVM;
import edu.stanford.cs.svm.SVMMethod;

class GLine_new extends SVMMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("GLine.new", "DDDD");
      double y2 = svm.popDouble();
      double x2 = svm.popDouble();
      double y1 = svm.popDouble();
      double x1 = svm.popDouble();
      svm.push(Value.createObject(new GLine(x1, y1, x2, y2), "GLine"));
   }
}
