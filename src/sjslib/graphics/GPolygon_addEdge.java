package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;

class GPolygon_addEdge extends GPolygonMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("GPolygon.addEdge", "DD");
      double dy = svm.popDouble();
      double dx = svm.popDouble();
      this.getGPolygon(svm, receiver).addEdge(dx, dy);
      svm.push(Value.UNDEFINED);
   }
}
