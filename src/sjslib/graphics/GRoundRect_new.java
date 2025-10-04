package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.graphics.GRoundRect;
import edu.stanford.cs.svm.SVM;
import edu.stanford.cs.svm.SVMMethod;

class GRoundRect_new extends SVMMethod {
   public void execute(SVM svm, Value receiver) {
      double corner;
      double height;
      double width;
      if (svm.getArgumentCount() == 2) {
         svm.checkSignature("GRoundRect.new", "DDD");
         corner = svm.popDouble();
         height = svm.popDouble();
         width = svm.popDouble();
         GRoundRect rr = new GRoundRect(0.0D, 0.0D, width, height, corner);
         svm.push(Value.createObject(rr, "GRoundRect"));
      } else {
         svm.checkSignature("GRoundRect.new", "DDDDD");
         corner = svm.popDouble();
         height = svm.popDouble();
         width = svm.popDouble();
         double y = svm.popDouble();
         double x = svm.popDouble();
         GRoundRect rr = new GRoundRect(x, y, width, height, corner);
         svm.push(Value.createObject(rr, "GRoundRect"));
      }

   }
}
