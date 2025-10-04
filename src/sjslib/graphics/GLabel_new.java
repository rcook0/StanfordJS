package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.graphics.GLabel;
import edu.stanford.cs.svm.SVM;
import edu.stanford.cs.svm.SVMMethod;

class GLabel_new extends SVMMethod {
   public void execute(SVM svm, Value receiver) {
      if (svm.getArgumentCount() == 3) {
         svm.checkSignature("GLabel.new", "SDD");
         double y = svm.popDouble();
         double x = svm.popDouble();
         String str = svm.popString();
         svm.push(Value.createObject(new GLabel(str, x, y), "GLabel"));
      } else {
         svm.checkSignature("GLabel.new", "S");
         String str = svm.popString();
         svm.push(Value.createObject(new GLabel(str), "GLabel"));
      }

   }
}
