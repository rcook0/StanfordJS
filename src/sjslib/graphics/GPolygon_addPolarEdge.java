package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;

class GPolygon_addPolarEdge extends GPolygonMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("GPolygon.addPolarEdge", "DD");
      double theta = svm.popDouble();
      double r = svm.popDouble();
      this.getGPolygon(svm, receiver).addPolarEdge(r, theta);
      svm.push(Value.UNDEFINED);
   }
}
