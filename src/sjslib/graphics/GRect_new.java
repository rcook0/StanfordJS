package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.graphics.GRect;
import edu.stanford.cs.svm.SVM;
import edu.stanford.cs.svm.SVMMethod;

class GRect_new extends SVMMethod {
   public void execute(SVM svm, Value receiver) {
      double height;
      double width;
      if (svm.getArgumentCount() == 2) {
         svm.checkSignature("GRect.new", "DD");
         height = svm.popDouble();
         width = svm.popDouble();
         svm.push(Value.createObject(new GRect(0.0D, 0.0D, width, height), "GRect"));
      } else {
         svm.checkSignature("GRect.new", "DDDD");
         height = svm.popDouble();
         width = svm.popDouble();
         double y = svm.popDouble();
         double x = svm.popDouble();
         svm.push(Value.createObject(new GRect(x, y, width, height), "GRect"));
      }

   }
}
