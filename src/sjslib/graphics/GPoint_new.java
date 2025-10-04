package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.graphics.GPoint;
import edu.stanford.cs.svm.SVM;
import edu.stanford.cs.svm.SVMMethod;

class GPoint_new extends SVMMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("GPoint.new", "DD");
      double y = svm.popDouble();
      double x = svm.popDouble();
      svm.push(Value.createObject(new GPoint(x, y), "GPoint"));
   }
}
