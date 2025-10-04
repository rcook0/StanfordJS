package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.graphics.GTransform;
import edu.stanford.cs.svm.SVM;
import edu.stanford.cs.svm.SVMMethod;

class GTransform_new extends SVMMethod {
   public void execute(SVM svm, Value receiver) {
      int nArgs = svm.getArgumentCount();
      if (nArgs == 0) {
         svm.checkSignature("GTransform.new", "");
         svm.push(Value.createObject(new GTransform(), "GTransform"));
      } else if (nArgs == 1) {
         svm.checkSignature("GTransform.new", "O");
         GTransform t = (GTransform)svm.pop().getValue();
         svm.push(Value.createObject(new GTransform(t), "GTransform"));
      } else {
         double d;
         double c;
         double b;
         double a;
         if (nArgs == 4) {
            svm.checkSignature("GTransform.new", "DDDD");
            d = svm.popDouble();
            c = svm.popDouble();
            b = svm.popDouble();
            a = svm.popDouble();
            svm.push(Value.createObject(new GTransform(a, b, c, d), "GTransform"));
         } else {
            svm.checkSignature("GTransform.new", "DDDDDD");
            d = svm.popDouble();
            c = svm.popDouble();
            b = svm.popDouble();
            a = svm.popDouble();
            double b = svm.popDouble();
            double a = svm.popDouble();
            svm.push(Value.createObject(new GTransform(a, b, a, b, c, d), "GTransform"));
         }
      }

   }
}
