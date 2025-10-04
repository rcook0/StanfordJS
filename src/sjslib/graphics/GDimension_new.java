package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.graphics.GDimension;
import edu.stanford.cs.svm.SVM;
import edu.stanford.cs.svm.SVMMethod;

class GDimension_new extends SVMMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("GDimension.new", "DD");
      double height = svm.popDouble();
      double width = svm.popDouble();
      GDimension size = new GDimension(width, height);
      svm.push(Value.createObject(size, "GDimension"));
   }
}
