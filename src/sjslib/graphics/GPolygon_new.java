package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.graphics.GPolygon;
import edu.stanford.cs.svm.SVM;
import edu.stanford.cs.svm.SVMMethod;

class GPolygon_new extends SVMMethod {
   public void execute(SVM svm, Value receiver) {
      if (svm.getArgumentCount() == 2) {
         svm.checkSignature("GPolygon.new", "DD");
         double y = svm.popDouble();
         double x = svm.popDouble();
         svm.push(Value.createObject(new GPolygon(x, y), "GPolygon"));
      } else {
         svm.checkSignature("GPolygon.new", "");
         svm.push(Value.createObject(new GPolygon(), "GPolygon"));
      }

   }
}
