package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.graphics.GRectangle;
import edu.stanford.cs.svm.SVM;
import edu.stanford.cs.svm.SVMMethod;

class GRectangle_new extends SVMMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("GRectangle.new", "DDDD");
      double height = svm.popDouble();
      double width = svm.popDouble();
      double y = svm.popDouble();
      double x = svm.popDouble();
      svm.push(Value.createObject(new GRectangle(x, y, width, height), "GRectangle"));
   }
}
