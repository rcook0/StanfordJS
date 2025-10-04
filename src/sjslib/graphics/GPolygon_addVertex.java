package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;

class GPolygon_addVertex extends GPolygonMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("GPolygon.addVertex", "DD");
      double y = svm.popDouble();
      double x = svm.popDouble();
      this.getGPolygon(svm, receiver).addVertex(x, y);
      svm.push(Value.UNDEFINED);
   }
}
