package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.graphics.GOval;
import edu.stanford.cs.svm.SVM;
import edu.stanford.cs.svm.SVMMethod;

class GOval_new extends SVMMethod {
   public void execute(SVM svm, Value receiver) {
      double height;
      double width;
      if (svm.getArgumentCount() == 2) {
         svm.checkSignature("GOval.new", "DD");
         height = svm.popDouble();
         width = svm.popDouble();
         svm.push(Value.createObject(new GOval(0.0D, 0.0D, width, height), "GOval"));
      } else {
         svm.checkSignature("GOval.new", "DDDD");
         height = svm.popDouble();
         width = svm.popDouble();
         double y = svm.popDouble();
         double x = svm.popDouble();
         svm.push(Value.createObject(new GOval(x, y, width, height), "GOval"));
      }

   }
}
